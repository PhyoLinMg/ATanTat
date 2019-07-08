package com.elemental.atantat.db.dao

import androidx.room.*
import com.elemental.atantat.data.models.Period

@Dao
interface PeriodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(periods:List<Period>)

    @Query("SELECT * from periods")
    fun periods():List<Period>

    @Query("DELETE FROM periods")
    fun deleteTable()


}