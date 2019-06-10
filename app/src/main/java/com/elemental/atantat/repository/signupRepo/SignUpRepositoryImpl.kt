package com.elemental.atantat.repository.signupRepo

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import com.elemental.atantat.data.models.SignUpUser
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.UserLoginSignUpInterface
import com.elemental.atantat.ui.Activity.MainActivity
import com.elemental.atantat.utils.SharedPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SignUpRepositoryImpl(val context: Context) : SignUpRepository, CoroutineScope {

    private val mJob: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Default + mJob
    val sharedPreference:SharedPreference= SharedPreference(context)
    private var api: UserLoginSignUpInterface = UserLoginSignUpInterface.invoke(ConnectivityInterceptorImpl(context))
    override fun signup(name: String, email: String, password: String, password_confirmation: String,activity:FragmentActivity?) {
        val signUpUser= SignUpUser(name, email, password, password_confirmation)
        launch {
            val response=api.signup(signUpUser).await()

            when{
                response.isSuccessful->{
                    val intent = Intent (context, MainActivity::class.java)
                    context.startActivity(intent)
                    activity?.finish()
                }

            }
        }

    }

    override fun cancelJob() {
        mJob.cancel()

    }
}