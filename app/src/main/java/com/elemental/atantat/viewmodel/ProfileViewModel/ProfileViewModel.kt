package com.elemental.atantat.viewmodel.ProfileViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.elemental.atantat.data.models.Profile
import com.elemental.atantat.data.responses.ProfileResponse
import com.elemental.atantat.repository.userRepo.UserRepository
import com.elemental.atantat.utils.DataLoadState

class ProfileViewModel(private val userRepository:UserRepository):ViewModel() {
    fun getDataLoadState(): LiveData<DataLoadState>{
        return userRepository.getDataLoadState()
    }
    fun loadUser(){
        return userRepository.loadUser()

    }
    fun getUser(): LiveData<Profile>{
        return userRepository.getUser()
    }
    fun cancelJob(){
        return userRepository.cancelJob()
    }
}