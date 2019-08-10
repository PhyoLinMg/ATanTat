package com.elemental.atantat.usecases

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.network.services.UserLoginSignUpInterface
import com.elemental.atantat.utils.DataLoadState
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostAttendanceUseCase(val context: Context,val sharedPreference: SharedPreference) :CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private val datastate: MutableLiveData<DataLoadState> = MutableLiveData()

    private var api:MainService= MainService.invoke(ConnectivityInterceptorImpl(context),sharedPreference.getValueString("token")!!)

    fun go(yesno:YesNo){
        Log.d("yesno",yesno.toString())
        datastate.postValue(DataLoadState.LOADING)

        GlobalScope.launch {
            val response=api.postattendancesAsync(yesno).await()
            try{
                when{
                    response.isSuccessful-> {
                        datastate.postValue(DataLoadState.SUCCESS)
                    }
                }
            }catch (e: NoConnectivityException) {
                Log.e("MY_ERROR", "No Internet Connection")
                datastate.postValue(DataLoadState.FAILED)
            } catch (e: Throwable) {
                Log.e("MY_ERROR", "I don't know! $e")
                datastate.postValue(DataLoadState.FAILED)
            }

        }
    }
    fun datastate() : LiveData<DataLoadState> {
        return datastate
    }

}