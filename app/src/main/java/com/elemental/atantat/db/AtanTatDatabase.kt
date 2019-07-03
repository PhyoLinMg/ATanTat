package com.elemental.atantat.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.elemental.atantat.data.models.Period


@Database(
    //gonna import all the model class
    entities = arrayOf(Period::class),
    version = 1,
    exportSchema = false
)
abstract class AtanTatDatabase:RoomDatabase() {
    abstract fun PeriodDao():PeriodDao

    companion object {
        @Volatile private var instance: AtanTatDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,
            AtanTatDatabase::class.java, "atantat.db"
        ).build()
    }
}