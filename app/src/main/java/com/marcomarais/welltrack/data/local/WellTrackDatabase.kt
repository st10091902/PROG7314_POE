package com.marcomarais.welltrack.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [MealEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WellTrackDatabase : RoomDatabase() {

    abstract fun mealDao(): MealDao

    companion object {
        @Volatile
        private var INSTANCE: WellTrackDatabase? = null

        fun getInstance(context: Context): WellTrackDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    WellTrackDatabase::class.java,
                    "welltrack.db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
