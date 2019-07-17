package com.elemental.atantat.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.elemental.atantat.data.models.Subject


@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subjects:List<Subject>)

    @Query("SELECT * from subjects")
    fun subjects():List<Subject>

    @Query("SELECT * from subjects WHERE id= :id")
    fun subject(id:Int) :List<Subject>

    @Query("DELETE FROM subjects")
    fun deleteTable()
}