package com.elemental.atantat.repository.loginRepo

import android.content.Context
import android.content.Intent

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.elemental.atantat.data.models.LoginUser
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.UserLoginSignUpInterface
import com.elemental.atantat.ui.Activity.MainActivity
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class LoginRepositoryImpl(val context: Context) : LoginRepository,CoroutineScope {
    private val mJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob
    val sharedPreference:SharedPreference= SharedPreference(context)
    private var api: UserLoginSignUpInterface = UserLoginSignUpInterface.invoke(ConnectivityInterceptorImpl(context))
    override fun login(email:String,password:String,activity: FragmentActivity?) {
       val loginUser= LoginUser(email, password,true)

        launch {
            val response=api.login(loginUser).await()
            when{
                response.isSuccessful->{
                    sharedPreference.save("token",response.body()!!.accessToken)
                    if (sharedPreference.getValueString("token")!=null) {
                        val intent = Intent (context, MainActivity::class.java)
                        context.startActivity(intent)
                        activity?.finish()
                    }
                    else{
                        Toast.makeText(context,"Error",Toast.LENGTH_SHORT).show()
                    }
                }


            }
        }


    }
    override fun cancelJob() {
        mJob.cancel()
    }
}