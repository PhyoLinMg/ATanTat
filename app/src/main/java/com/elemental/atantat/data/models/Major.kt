package com.elemental.atantat.data.models


import androidx.room.Entity
import com.google.gson.annotations.SerializedName
@Entity(tableName = "majors")
data class Major(
    val id: Int,
    val name: String,
    val year: Int
)