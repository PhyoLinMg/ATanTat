package com.elemental.atantat.repository.signupRepo

import androidx.fragment.app.FragmentActivity

interface SignUpRepository {

    fun signup(name:String,email:String,password:String,password_confirmation:String,activity: FragmentActivity?)
    fun cancelJob()
}