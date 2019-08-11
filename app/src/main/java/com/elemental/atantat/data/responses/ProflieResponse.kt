package com.elemental.atantat.data.responses


import com.google.gson.annotations.SerializedName

data class ProflieResponse(
    val email: String,
    val major: String,
    val name: String,
    val university: String
)