package com.example.data.repository

import com.example.data.local.StreakDao
import com.example.data.local.StreakLogEntity
import com.example.data.local.UserStreakEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class StreakRepository(private val streakDao: StreakDao) {

    val streakFlow: Flow<UserStreakEntity?> = streakDao.getStreakFlow()
    val recentLogsFlow: Flow<List<StreakLogEntity>> = streakDao.getRecentLogsFlow(10)

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    suspend fun initializeIfEmpty() {
        val existing = streakDao.getStreakSync()
        if (existing == null) {
            val today = dateFormat.format(Date())
            val initialStreak = UserStreakEntity(
                id = 1,
                currentStreak = 7,
                longestStreak = 14,
                lastActiveDate = today,
                streakFreezeCount = 2,
                totalDaysActive = 21,
                weeklyActiveDays = "1,1,1,1,1,1,1" // Mon to Sun active
            )
            streakDao.insertOrUpdateStreak(initialStreak)

            // Seed initial activity logs
            val initialLogs = listOf(
                StreakLogEntity(
                    dateString = today,
                    activityType = "AI_TUTOR_CHAT",
                    xpEarned = 15,
                    description = "Practiced café ordering with Camille"
                ),
                StreakLogEntity(
                    dateString = today,
                    activityType = "LESSON_COMPLETED",
                    xpEarned = 20,
                    description = "Completed 'Everyday Words' lesson"
                ),
                StreakLogEntity(
                    dateString = today,
                    activityType = "DAILY_PRACTICE",
                    xpEarned = 10,
                    description = "Spaced repetition review flashcards"
                )
            )
            for (log in initialLogs) {
                streakDao.insertLog(log)
            }
        }
    }

    suspend fun recordEngagement(activityType: String, xpEarned: Int, description: String) {
        val current = streakDao.getStreakSync() ?: run {
            initializeIfEmpty()
            streakDao.getStreakSync()!!
        }

        val todayDate = Date()
        val todayStr = dateFormat.format(todayDate)

        var newStreak = current.currentStreak
        var newStreakFreeze = current.streakFreezeCount
        var totalDays = current.totalDaysActive

        if (current.lastActiveDate != todayStr) {
            val lastDate = runCatching { dateFormat.parse(current.lastActiveDate) }.getOrNull()
            if (lastDate != null) {
                val diffMillis = todayDate.time - lastDate.time
                val daysDiff = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()

                if (daysDiff == 1) {
                    // Consecutive day streak increase!
                    newStreak += 1
                    totalDays += 1
                } else if (daysDiff > 1) {
                    if (newStreakFreeze > 0) {
                        // Protect streak with streak freeze
                        newStreakFreeze -= 1
                        newStreak += 1
                        totalDays += 1
                    } else {
                        // Streak reset
                        newStreak = 1
                        totalDays += 1
                    }
                }
            } else {
                newStreak += 1
                totalDays += 1
            }
        }

        val longest = maxOf(current.longestStreak, newStreak)

        // Calculate active days for the week (Monday = 0, Sunday = 6)
        val calendar = Calendar.getInstance()
        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) // 1 = Sunday, 2 = Monday, ...
        val dayIndex = if (dayOfWeek == Calendar.SUNDAY) 6 else dayOfWeek - 2

        val activeDaysList = current.weeklyActiveDays.split(",").map { it.trim().toIntOrNull() ?: 0 }.toMutableList()
        while (activeDaysList.size < 7) {
            activeDaysList.add(0)
        }
        if (dayIndex in 0..6) {
            activeDaysList[dayIndex] = 1
        }
        val weeklyStr = activeDaysList.joinToString(",")

        val updatedStreak = current.copy(
            currentStreak = newStreak,
            longestStreak = longest,
            lastActiveDate = todayStr,
            streakFreezeCount = newStreakFreeze,
            totalDaysActive = totalDays,
            weeklyActiveDays = weeklyStr,
            lastUpdatedTimestamp = System.currentTimeMillis()
        )
        streakDao.insertOrUpdateStreak(updatedStreak)

        streakDao.insertLog(
            StreakLogEntity(
                dateString = todayStr,
                activityType = activityType,
                xpEarned = xpEarned,
                description = description
            )
        )
    }

    suspend fun incrementStreakManual(): Int {
        val current = streakDao.getStreakSync() ?: run {
            initializeIfEmpty()
            streakDao.getStreakSync()!!
        }
        val newStreak = current.currentStreak + 1
        val newLongest = maxOf(current.longestStreak, newStreak)
        val updated = current.copy(
            currentStreak = newStreak,
            longestStreak = newLongest,
            totalDaysActive = current.totalDaysActive + 1,
            lastActiveDate = dateFormat.format(Date()),
            lastUpdatedTimestamp = System.currentTimeMillis()
        )
        streakDao.insertOrUpdateStreak(updated)
        streakDao.insertLog(
            StreakLogEntity(
                dateString = dateFormat.format(Date()),
                activityType = "BONUS_STREAK",
                xpEarned = 25,
                description = "Day streak extended to $newStreak days!"
            )
        )
        return newStreak
    }

    suspend fun addStreakFreeze(): Boolean {
        val current = streakDao.getStreakSync() ?: return false
        val updated = current.copy(streakFreezeCount = current.streakFreezeCount + 1)
        streakDao.insertOrUpdateStreak(updated)
        return true
    }
}
