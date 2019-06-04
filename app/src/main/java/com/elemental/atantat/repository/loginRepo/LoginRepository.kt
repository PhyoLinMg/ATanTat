package com.elemental.atantat.repository.loginRepo

interface LoginRepository {

     fun login(email:String,password:String)
    fun cancelJob()

}