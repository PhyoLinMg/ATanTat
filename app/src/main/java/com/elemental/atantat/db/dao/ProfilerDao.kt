package com.elemental.atantat.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elemental.atantat.data.models.Profile

@Dao
interface ProfilerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(profile: Profile)

    @Query("SELECT * from profile")
    fun profile():Profile


    @Query("DELETE FROM profile")
    fun deleteTable()
}