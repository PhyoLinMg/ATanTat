package com.elemental.atantat.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("email")
    val email: String,
    @SerializedName("major")
    val major: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("university")
    val university: String

)