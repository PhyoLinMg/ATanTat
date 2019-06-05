package com.elemental.atantat.data.models

data class LoginUser(
    val email:String,
    val password:String,
    val remember_me:Boolean
)