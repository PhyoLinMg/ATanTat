package com.elemental.atantat.viewmodel.ProfileViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elemental.atantat.repository.userRepo.UserRepository

class ProfileViewModelFactory (private val userRepository: UserRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProfileViewModel(userRepository) as T
    }
}