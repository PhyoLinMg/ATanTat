package com.elemental.atantat.repository.loginRepo

import android.content.Context
import android.util.Log
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.UserLoginSignUpInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.json.JSONObject
import kotlin.coroutines.CoroutineContext

class LoginRepositoryImpl(val context: Context) : LoginRepository,CoroutineScope {
    private val mJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob
    private var api: UserLoginSignUpInterface = UserLoginSignUpInterface.invoke(ConnectivityInterceptorImpl(context))
    override fun login(email:String,password:String) {
        val rootObject= JSONObject()
        rootObject.put("email",email)
        rootObject.put("password",password)
        rootObject.put("remember_me",true)

        launch {
            val response=api.login(rootObject.toString()).await()
            when{
                response.isSuccessful->{
                    Log.d("token",response.body()!!.accessToken)
                }

            }
        }


    }
    override fun cancelJob() {
        mJob.cancel()
    }
}