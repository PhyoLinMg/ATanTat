package com.elemental.atantat.data.responses


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("email")
    val email: String,
    @SerializedName("major")
    val major: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("university")
    val university: String
)