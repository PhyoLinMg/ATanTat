package com.elemental.atantat.usecases

import android.content.Context
import android.util.Log
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.network.services.UserLoginSignUpInterface
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class PostAttendanceUseCase(val context: Context,val sharedPreference: SharedPreference) :CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private var api:MainService= MainService.invoke(ConnectivityInterceptorImpl(context),sharedPreference.getValueString("token")!!)

    fun go(yesno:YesNo){

        Log.d("yesno",yesno.toString())
        GlobalScope.launch {
           val response=api.postattendancesAsync(yesno).await()
            Log.d("response",response.body().toString())
        }
    }

}