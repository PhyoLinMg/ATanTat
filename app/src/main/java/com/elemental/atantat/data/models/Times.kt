package com.elemental.atantat.data.models

import androidx.room.ColumnInfo

data class Times(
    @ColumnInfo(name = "startTime") val startTime: String?,
    @ColumnInfo(name = "endTime") val endTime: String?,
    @ColumnInfo(name = "subjectId") val subjectID: String?
)
