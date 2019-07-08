package com.elemental.atantat.viewmodel.LoginViewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.atantat.repository.loginRepo.LoginRepository
import com.elemental.atantat.utils.DataLoadState

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun login(email:String,password:String,uni_id:Int,major_id:Int,activity: FragmentActivity?){
        loginRepository.login(email,password,uni_id,major_id,activity)
    }

    fun getLoadState(): LiveData<DataLoadState> {
        return loginRepository.getDataLoadState()
    }
    fun cancelJob(){
        loginRepository.cancelJob()
    }


}