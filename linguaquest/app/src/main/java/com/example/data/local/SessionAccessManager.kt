package com.example.data.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Manages the user's free lesson session counter and lifetime access state.
 * Persists data locally so that completed free lessons and lifetime unlocks
 * survive application restarts.
 */
class SessionAccessManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "lingua_access_prefs"
        private const val KEY_FREE_LESSONS_COMPLETED = "free_lessons_completed"
        private const val KEY_LIFETIME_UNLOCKED = "lifetime_unlocked"

        const val MAX_FREE_LESSONS = 3
        const val VALID_ACCESS_KEY = "AIPro999"
        const val LINKTREE_URL = "https://linktr.ee/admin"
    }

    /**
     * Number of free lessons completed by the user.
     */
    var freeLessonsCompleted: Int
        get() = prefs.getInt(KEY_FREE_LESSONS_COMPLETED, 0)
        set(value) {
            prefs.edit().putInt(KEY_FREE_LESSONS_COMPLETED, value).apply()
        }

    /**
     * Whether the user has unlocked permanent lifetime access.
     */
    var hasLifetimeAccess: Boolean
        get() = prefs.getBoolean(KEY_LIFETIME_UNLOCKED, false)
        set(value) {
            prefs.edit().putBoolean(KEY_LIFETIME_UNLOCKED, value).apply()
        }

    /**
     * The app is locked if the user has completed 3 or more lessons
     * and has not unlocked lifetime access.
     */
    val isLocked: Boolean
        get() = !hasLifetimeAccess && freeLessonsCompleted >= MAX_FREE_LESSONS

    /**
     * Increments the completed lesson counter by 1 and returns the updated count.
     */
    fun incrementCompletedLessons(): Int {
        val next = freeLessonsCompleted + 1
        freeLessonsCompleted = next
        return next
    }

    /**
     * Validates an access key. If it matches 'AIPro999', lifetime access is granted permanently.
     */
    fun validateAndUnlock(key: String): Boolean {
        if (key.trim() == VALID_ACCESS_KEY) {
            hasLifetimeAccess = true
            return true
        }
        return false
    }

    /**
     * Resets the session counter and access state (useful for testing and debugging).
     */
    fun resetForTesting() {
        prefs.edit().clear().apply()
    }
}
