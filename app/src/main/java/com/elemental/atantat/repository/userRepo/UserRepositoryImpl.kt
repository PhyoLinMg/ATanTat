package com.elemental.atantat.repository.userRepo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.Profile

import com.elemental.atantat.data.responses.ProfileResponse
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UserRepositoryImpl(val context: Context) : UserRepository,CoroutineScope {
    private val mJob: Job = Job()
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()
    private val profile: MutableLiveData<Profile> = MutableLiveData()
    private val sharedPreference: SharedPreference = SharedPreference(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob
    private val api: MainService = MainService.invoke(ConnectivityInterceptorImpl(context),sharedPreference.getValueString("token")!!)
    private val db: AtanTatDatabase = AtanTatDatabase.invoke(context)

    override fun getDataLoadState(): LiveData<DataLoadState> {
        return dataLoadState
    }

    override fun loadUser() {
        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            try {
                val response = api.getUserAsync().await()
                when {
                    response.isSuccessful -> {
                        db.ProfilerDao().deleteTable()
                        db.ProfilerDao().insert(response.body()!!)
                    }
                }



            } catch (e: NoConnectivityException) {
                Log.e("MY_ERROR", "No Internet Connection But You can use offline")
                dataLoadState.postValue(DataLoadState.FAILED)
            } catch (e: Throwable) {
                Log.e("MY_ERROR", "I don't know! $e")
                dataLoadState.postValue(DataLoadState.FAILED)
            }
            profile.postValue(db.ProfilerDao().profile())

        }
    }

    override fun getUser(): LiveData<Profile> {
        return profile
    }

    override fun cancelJob() {
        mJob.cancel()
    }
}