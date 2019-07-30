package com.elemental.atantat.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.elemental.atantat.data.models.Subject
import com.elemental.atantat.data.models.YesNo


@Dao
interface SubjectDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(subjects:List<Subject>)

    @Query("SELECT * from subjects")
    fun subjects():List<Subject>

    @Query("SELECT * from subjects WHERE id= :id")
    fun subject(id:Int) :List<Subject>

    @Query("SELECT id,yes,`no` from subjects")
    fun getattendence():List<YesNo>


    @Query("DELETE FROM subjects")
    fun deleteTable()

    @Update
    fun update(subject: Subject)

}