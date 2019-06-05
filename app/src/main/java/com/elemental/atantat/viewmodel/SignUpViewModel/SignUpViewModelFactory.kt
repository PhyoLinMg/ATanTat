package com.elemental.atantat.viewmodel.SignUpViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.repository.signupRepo.SignUpRepository
import com.elemental.atantat.viewmodel.LoginViewModel.LoginViewModel

class SignUpViewModelFactory (private val signUpRepository: SignUpRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SignUpViewModel(signUpRepository) as T
    }
}