package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_streak")
data class UserStreakEntity(
    @PrimaryKey val id: Int = 1,
    val currentStreak: Int = 7,
    val longestStreak: Int = 14,
    val lastActiveDate: String, // YYYY-MM-DD
    val streakFreezeCount: Int = 2,
    val totalDaysActive: Int = 21,
    val weeklyActiveDays: String = "1,1,1,1,1,1,1", // Mon-Sun active flags
    val lastUpdatedTimestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "streak_logs")
data class StreakLogEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateString: String,
    val activityType: String, // e.g. "AI_TUTOR_CHAT", "LESSON_COMPLETED", "PRACTICE"
    val xpEarned: Int,
    val description: String,
    val timestamp: Long = System.currentTimeMillis()
)
