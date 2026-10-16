package com.example.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface StreakDao {
    @Query("SELECT * FROM user_streak WHERE id = 1 LIMIT 1")
    fun getStreakFlow(): Flow<UserStreakEntity?>

    @Query("SELECT * FROM user_streak WHERE id = 1 LIMIT 1")
    suspend fun getStreakSync(): UserStreakEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateStreak(streak: UserStreakEntity)

    @Query("SELECT * FROM streak_logs ORDER BY timestamp DESC LIMIT :limit")
    fun getRecentLogsFlow(limit: Int = 10): Flow<List<StreakLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLog(log: StreakLogEntity)

    @Query("DELETE FROM streak_logs")
    suspend fun clearLogs()
}
