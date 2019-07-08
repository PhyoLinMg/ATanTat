package com.elemental.atantat.repository.universityRepo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.University
import com.elemental.atantat.db.AtanTatDatabase
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.PublicService
import com.elemental.atantat.utils.DataLoadState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class UniversityRepositoryImpl(val context: Context): UniversityRepository,CoroutineScope {
    private val mJob: Job = Job()
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()

    private val universities: MutableLiveData<List<University>> = MutableLiveData()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob

    private val api: PublicService = PublicService.invoke(ConnectivityInterceptorImpl(context))
    private val db: AtanTatDatabase = AtanTatDatabase.invoke(context)
    override fun getDataLoadState(): LiveData<DataLoadState> {
        return dataLoadState
    }

    override fun loadUniversities() {
        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            try {
                val response=api.getUniversitiesAsync().await()
                Log.d("response",response.body()!!.universities.toString())
                when {
                    response.isSuccessful ->  {
                        Log.d("uni",response.body()!!.universities.toString())
                        universities.postValue(response.body()!!.universities)
                        Log.d("list",universities.toString())
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

    override fun getUniversities(): LiveData<List<University>> {
        return universities
    }

    override fun cancelJob() {
        mJob.cancel()

    }
}