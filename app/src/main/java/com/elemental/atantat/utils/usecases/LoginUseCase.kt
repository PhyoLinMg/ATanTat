package com.elemental.atantat.utils.usecases

import org.json.JSONObject

class LoginUseCase {


    public fun login(email:String,password:String){
        val rootObject= JSONObject()
        rootObject.put("email",email)
        rootObject.put("password",password)
        rootObject.put("remember_me",true)
    }
}
