package com.elemental.atantat.viewmodel.LoginViewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.elemental.atantat.repository.loginRepo.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun login(email:String,password:String,activity: FragmentActivity?){
        loginRepository.login(email,password,activity)
    }
    fun cancelJob(){
        loginRepository.cancelJob()
    }


}