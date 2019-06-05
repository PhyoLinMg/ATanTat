package com.elemental.atantat.viewmodel.LoginViewModel

import androidx.lifecycle.ViewModel
import com.elemental.atantat.repository.loginRepo.LoginRepository

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {
    // TODO: Implement the ViewModel

    fun login(email:String,password:String){
        loginRepository.login(email,password)
    }
    fun cancelJob(){
        loginRepository.cancelJob()
    }


}