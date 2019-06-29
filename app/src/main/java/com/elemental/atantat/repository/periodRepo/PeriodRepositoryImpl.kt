package com.elemental.atantat.repository.periodRepo

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.Period
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.MainService
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PeriodRepositoryImpl(val context:Context) : PeriodRepository, CoroutineScope {

    private val mJob: Job = Job()
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()
    private val periods: MutableLiveData<List<Period>> = MutableLiveData()
    private val sharedPreference:SharedPreference=SharedPreference(context)

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob

    private val api:MainService= MainService.invoke(ConnectivityInterceptorImpl(context),sharedPreference.getValueString("token")!!)


    override fun loadPeriod(){

        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            try {
                val response=api.getPeriodsAsync().await()
                Log.d("api",response.body()!!.periods.toString())
                when {
                    response.isSuccessful ->  {
                        periods.postValue(response.body()!!.periods)
                        dataLoadState.postValue(DataLoadState.LOADED)
                        Log.d("period",periods.toString())
                    }
                }
            } catch (e: NoConnectivityException) {
                Log.e("MY_ERROR", "No Internet Connection")
                dataLoadState.postValue(DataLoadState.FAILED)
            } catch (e: Throwable) {
                Log.e("MY_ERROR", "I don't know! $e")
                dataLoadState.postValue(DataLoadState.FAILED)
            }
        }
//        launch {
//            val response=api.getPeriodsAsync().await()
//            try {
//                when{
//                    response.isSuccessful->{
//                        dataLoadState.postValue(DataLoadState.LOADED)
//
//                        Log.d("period",response.toString())
//                    }
//
//                }
//            }catch (e: NoConnectivityException) {
//                Log.e("MY_ERROR", "No Internet Connection")
//                dataLoadState.postValue(DataLoadState.FAILED)
//            } catch (e: Throwable) {
//                Log.e("MY_ERROR", "I don't know! $e")
//                dataLoadState.postValue(DataLoadState.FAILED)
//            }
//        }

    }
    override fun getPeriod(): LiveData<List<Period>> {
        return periods
    }

    override fun cancelJob() {
        mJob.cancel()
    }
    override fun getDataLoadState(): LiveData<DataLoadState> {
        return dataLoadState
    }
}