package com.elemental.atantat.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elemental.atantat.data.models.Major

@Dao
interface MajorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(periods:List<Major>)

    @Query("SELECT * from majors")
    fun periods():List<Major>

    @Query("DELETE FROM majors")
    fun deleteTable()
}