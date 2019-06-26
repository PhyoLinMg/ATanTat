package com.elemental.atantat.repository.loginRepo

import android.content.Context
import android.content.Intent

import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.LoginUser
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.UserLoginSignUpInterface
import com.elemental.atantat.ui.Activity.MainActivity
import com.elemental.atantat.utils.DataLoadState
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
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()
    val sharedPreference:SharedPreference= SharedPreference(context)
    private var api: UserLoginSignUpInterface = UserLoginSignUpInterface.invoke(ConnectivityInterceptorImpl(context))
    override fun login(email:String,password:String,activity: FragmentActivity?) {
       val loginUser= LoginUser(email, password,true)
        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            val response=api.login(loginUser).await()
            when{
                response.isSuccessful->{

                    sharedPreference.save("token",response.body()!!.accessToken)

                    if (sharedPreference.getValueString("token")!=null) {
                        dataLoadState.postValue(DataLoadState.LOADED)
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
    override fun getDataLoadState(): LiveData<DataLoadState> {
        return dataLoadState
    }
}