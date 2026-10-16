package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("LinguaQuest", appName)
  }

  @Test
  fun `verify AdMob Application ID configured in AndroidManifest`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val packageManager = context.packageManager
    val appInfo = packageManager.getApplicationInfo(context.packageName, android.content.pm.PackageManager.GET_META_DATA)
    val adMobAppId = appInfo.metaData?.getString("com.google.android.gms.ads.APPLICATION_ID")
    org.junit.Assert.assertEquals("ca-app-pub-3940256099942544~3347511713", adMobAppId)
  }

  @Test
  fun `verify AdMobManager initializes safely`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    com.example.ads.AdMobManager.initialize(context)
    org.junit.Assert.assertNotNull(com.example.ads.AdMobManager.initializationSummary.value)
  }

  @Test
  fun `verify session counter allows exactly 3 free lessons then locks permanently`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val accessManager = com.example.data.local.SessionAccessManager(context)
    accessManager.resetForTesting()

    // 0 lessons completed: unlocked
    org.junit.Assert.assertEquals(0, accessManager.freeLessonsCompleted)
    org.junit.Assert.assertFalse(accessManager.hasLifetimeAccess)
    org.junit.Assert.assertFalse(accessManager.isLocked)

    // Complete lesson 1: unlocked
    val c1 = accessManager.incrementCompletedLessons()
    org.junit.Assert.assertEquals(1, c1)
    org.junit.Assert.assertFalse(accessManager.isLocked)

    // Complete lesson 2: unlocked
    val c2 = accessManager.incrementCompletedLessons()
    org.junit.Assert.assertEquals(2, c2)
    org.junit.Assert.assertFalse(accessManager.isLocked)

    // Complete lesson 3: LOCKED!
    val c3 = accessManager.incrementCompletedLessons()
    org.junit.Assert.assertEquals(3, c3)
    org.junit.Assert.assertTrue("App must be permanently locked after 3rd lesson", accessManager.isLocked)

    // Test invalid access key rejected
    val invalidResult = accessManager.validateAndUnlock("WrongCode123")
    org.junit.Assert.assertFalse(invalidResult)
    org.junit.Assert.assertTrue("App must remain locked with invalid key", accessManager.isLocked)

    // Test official unlock key 'AIPro999' grants lifetime access
    val unlockResult = accessManager.validateAndUnlock("AIPro999")
    org.junit.Assert.assertTrue("Valid key must unlock", unlockResult)
    org.junit.Assert.assertTrue("User must have lifetime access", accessManager.hasLifetimeAccess)
    org.junit.Assert.assertFalse("App must not be locked after unlocking", accessManager.isLocked)

    // Test persistence across manager instances
    val restoredManager = com.example.data.local.SessionAccessManager(context)
    org.junit.Assert.assertTrue("Lifetime access must persist across instances", restoredManager.hasLifetimeAccess)
    org.junit.Assert.assertFalse("App remains unlocked across sessions", restoredManager.isLocked)

    // Verify constants required by prompt
    org.junit.Assert.assertEquals("https://linktr.ee/admin", com.example.data.local.SessionAccessManager.LINKTREE_URL)
    org.junit.Assert.assertEquals("AIPro999", com.example.data.local.SessionAccessManager.VALID_ACCESS_KEY)
    org.junit.Assert.assertEquals(3, com.example.data.local.SessionAccessManager.MAX_FREE_LESSONS)
  }
}
