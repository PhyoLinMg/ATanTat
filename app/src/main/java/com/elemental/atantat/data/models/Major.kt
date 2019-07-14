package com.elemental.atantat.data.models


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "majors")
data class Major(
    @SerializedName("id")
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("year")
    val year: Int
)