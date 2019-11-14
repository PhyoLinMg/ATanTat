package com.elemental.atantat.repository.userRepo

import androidx.lifecycle.LiveData
import com.elemental.atantat.data.models.Profile
import com.elemental.atantat.data.responses.ProfileResponse
import com.elemental.atantat.utils.DataLoadState

interface UserRepository {
    fun getDataLoadState(): LiveData<DataLoadState>
    fun loadUser()
    fun getUser(): LiveData<Profile>
    fun cancelJob()
}