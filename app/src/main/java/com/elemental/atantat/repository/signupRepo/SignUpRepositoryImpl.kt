package com.elemental.atantat.repository.signupRepo

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.elemental.atantat.data.models.SignUpUser
import com.elemental.atantat.network.ConnectivityInterceptorImpl
import com.elemental.atantat.network.NoConnectivityException
import com.elemental.atantat.network.services.UserLoginSignUpInterface
import com.elemental.atantat.ui.Activity.MainActivity
import com.elemental.atantat.utils.DataLoadState
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
    private val sharedPreference:SharedPreference= SharedPreference(context)
    private val dataLoadState: MutableLiveData<DataLoadState> = MutableLiveData()
    private var api: UserLoginSignUpInterface = UserLoginSignUpInterface.invoke(ConnectivityInterceptorImpl(context))


    override fun signup(name: String, email: String, password: String, password_confirmation: String,uni_id:Int,major_id:Int,activity:FragmentActivity?) {
        val signUpUser= SignUpUser(name, email, password, password_confirmation,uni_id,major_id)
        dataLoadState.postValue(DataLoadState.LOADING)
        launch {
            val response=api.signup(signUpUser).await()

            try {
                when{
                    response.isSuccessful->{
                        sharedPreference.save("token",response.body()!!.accessToken)
                        if(sharedPreference.getValueString("token")!=null) {
                            dataLoadState.postValue(DataLoadState.LOADED)
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                            activity?.finish()
                        }
                    }

                }
            }catch (e: NoConnectivityException) {
                Log.e("MY_ERROR", "No Internet Connection")
                dataLoadState.postValue(DataLoadState.FAILED)
            } catch (e: Throwable) {
                Log.e("MY_ERROR", "I don't know! $e")
                dataLoadState.postValue(DataLoadState.FAILED)
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