package com.elemental.atantat.usecases

import android.content.Context
import android.util.Log
import com.elemental.atantat.data.models.YesNo
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.services.MainService
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class PostAttendanceUseCase(val context: Context) :CoroutineScope{
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    private lateinit var sharedPreferences: SharedPreference

    fun go(list:List<YesNo>){
        sharedPreferences=SharedPreference(context)
        val token:String?=sharedPreferences.getValueString("token")
        val api:MainService= MainService.invoke(ConnectivityInterceptorImpl(context),token!!)
        launch {
           val response=api.postattendances(list).await()
            Log.d("response",response.toString())
        }
    }

}