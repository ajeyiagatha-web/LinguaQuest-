package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [UserStreakEntity::class, StreakLogEntity::class],
    version = 1,
    exportSchema = false
)
abstract class LinguaDatabase : RoomDatabase() {
    abstract fun streakDao(): StreakDao

    companion object {
        @Volatile
        private var INSTANCE: LinguaDatabase? = null

        fun getDatabase(context: Context): LinguaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LinguaDatabase::class.java,
                    "lingua_quest_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
