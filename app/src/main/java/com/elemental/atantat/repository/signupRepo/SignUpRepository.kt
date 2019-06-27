package com.elemental.atantat.repository.signupRepo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.elemental.atantat.utils.DataLoadState

interface SignUpRepository {

    fun signup(name:String,email:String,password:String,password_confirmation:String,activity: FragmentActivity?)
    fun getDataLoadState(): LiveData<DataLoadState>
    fun cancelJob()
}