package com.elemental.atantat.repository.majorRepo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.Major
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.PublicService
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MajorRepositoryImpl(val context: Context) : MajorRepository,CoroutineScope {

    private val mJob: Job = Job()
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()
    private val majors: MutableLiveData<List<Major>> = MutableLiveData()
    private val sharedPreference: SharedPreference = SharedPreference(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob

    private val api: PublicService = PublicService.invoke(ConnectivityInterceptorImpl(context))
    private val db: AtanTatDatabase = AtanTatDatabase.invoke(context)
    override fun getDataLoadState(): LiveData<DataLoadState> {
        return dataLoadState
    }

    override fun loadMajors() {
        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            try {
                val response=api.getMajorsAsync().await()
                when {
                    response.isSuccessful ->  {
                        majors.postValue(response.body()!!.majors)
                        dataLoadState.postValue(DataLoadState.LOADED)
                    }
                }
            } catch (e: NoConnectivityException) {
                Log.e("MY_ERROR", "No Internet Connection But You can use offline")
                dataLoadState.postValue(DataLoadState.FAILED)
            } catch (e: Throwable) {
                Log.e("MY_ERROR", "I don't know! $e")
                dataLoadState.postValue(DataLoadState.FAILED)
            }
        }

    }

    override fun getMajors(): LiveData<List<Major>> {
        return majors
    }

    override fun cancelJob() {
        mJob.cancel()
    }
}