package com.elemental.atantat.data.models

import com.google.gson.annotations.SerializedName


data class Subjects(
    @SerializedName("data")
    val subjects: List<Subject>
)