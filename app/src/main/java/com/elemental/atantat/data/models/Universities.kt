package com.elemental.atantat.data.models

import com.google.gson.annotations.SerializedName


data class Universities(
    @SerializedName("data")
    val universities: List<University>
)