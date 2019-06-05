package com.elemental.atantat.viewmodel.SignUpViewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import com.elemental.atantat.repository.signupRepo.SignUpRepository

class SignUpViewModel(private val signUpRepository: SignUpRepository):ViewModel() {
    fun signup(name:String,email:String,password:String,password_confirmation:String,activity: FragmentActivity?){
        signUpRepository.signup(name,email,password,password_confirmation,activity)
    }
    fun cancelJob(){
        signUpRepository.cancelJob()
    }
}