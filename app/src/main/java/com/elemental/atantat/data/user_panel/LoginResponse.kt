package com.elemental.atantat.data.user_panel


import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("expires_at")
    val expiresAt: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("message")
    val message:String
)