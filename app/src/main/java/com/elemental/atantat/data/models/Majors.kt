package com.elemental.atantat.data.models

import com.google.gson.annotations.SerializedName


data class Majors(
    @SerializedName("data")
    val majors: List<Major>
)