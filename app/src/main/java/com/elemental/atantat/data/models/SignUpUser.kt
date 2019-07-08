package com.elemental.atantat.data.models

data class SignUpUser(
    val name:String,
    val email:String,
    val password:String,
    val password_confirmation:String,
    val uni_id:Int,
    val major_id:Int
)