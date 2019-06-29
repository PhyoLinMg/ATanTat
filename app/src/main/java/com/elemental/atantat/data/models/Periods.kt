package com.elemental.atantat.data.models

import com.google.gson.annotations.SerializedName


data class Periods(
    @SerializedName("data")
    val periods: List<Period>
)