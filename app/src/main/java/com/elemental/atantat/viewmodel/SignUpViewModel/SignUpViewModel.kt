package com.elemental.atantat.viewmodel.SignUpViewModel

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.atantat.repository.signupRepo.SignUpRepository
import com.elemental.atantat.utils.DataLoadState

class SignUpViewModel(private val signUpRepository: SignUpRepository):ViewModel() {
    fun signup(name:String,email:String,password:String,password_confirmation:String,uni_id:Int,major_id:Int,activity: FragmentActivity?){
        signUpRepository.signup(name,email,password,password_confirmation,uni_id,major_id,activity)
    }
    fun getLoadState(): LiveData<DataLoadState> {
        return signUpRepository.getDataLoadState()
    }
    fun cancelJob(){
        signUpRepository.cancelJob()
    }
}