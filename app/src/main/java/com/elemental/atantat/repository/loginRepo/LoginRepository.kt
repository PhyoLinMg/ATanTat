package com.elemental.atantat.repository.loginRepo

import androidx.fragment.app.FragmentActivity

interface LoginRepository {

     fun login(email:String,password:String,activity: FragmentActivity?)
    fun cancelJob()

}