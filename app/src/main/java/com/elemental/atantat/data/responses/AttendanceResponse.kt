package com.elemental.atantat.data.responses

import com.google.gson.annotations.SerializedName


data class AttendanceResponse(
    @SerializedName("message")
    val message:String
)