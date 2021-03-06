package com.elemental.atantat.repository.loginRepo

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import com.elemental.atantat.utils.DataLoadState

interface LoginRepository {

     fun login(email:String,password:String,uni_id:Int,major_id:Int,activity: FragmentActivity?)
    fun getDataLoadState(): LiveData<DataLoadState>
    fun cancelJob()

}