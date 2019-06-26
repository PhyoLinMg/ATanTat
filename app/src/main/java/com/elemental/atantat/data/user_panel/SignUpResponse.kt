package com.elemental.atantat.data.user_panel

import com.google.gson.annotations.SerializedName


data class SignUpResponse(
    val message: String,
    @SerializedName("access_token")
    val accessToken: String
)