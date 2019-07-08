package com.elemental.atantat.data.models


import com.google.gson.annotations.SerializedName

data class University(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)