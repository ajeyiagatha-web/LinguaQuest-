package com.example

import com.example.data.local.StreakLogEntity
import com.example.data.local.UserStreakEntity
import com.example.model.*
import org.junit.Assert.*
import org.junit.Test

class ExampleUnitTest {
    @Test
    fun testInitialPathNodes() {
        val completedNodes = listOf(
            PathNode("node_1", "Greetings", NodeType.LESSON, NodeState.COMPLETED, 0, hasCrown = true),
            PathNode("node_2", "Basic Phrases", NodeType.PHRASES, NodeState.COMPLETED, 48, hasCrown = true),
            PathNode("node_3", "Everyday Words", NodeType.LESSON, NodeState.ACTIVE, 0)
        )
        assertEquals(3, completedNodes.size)
        assertTrue(completedNodes[0].hasCrown)
        assertEquals(NodeState.ACTIVE, completedNodes[2].state)
    }

    @Test
    fun testScenarioData() {
        val scenario = Scenario(
            id = "bistro",
            title = "At a Parisian Bistro",
            emoji = "🥐",
            mission = "Order a croissant and a café au lait naturally.",
            initialFrench = "Bonjour ! Bienvenue au Bistro des Étoiles. Qu'est-ce que je vous sers aujourd'hui ?",
            initialEnglish = "Hello! Welcome to Bistro des Étoiles. What can I get for you today?",
            suggestedReplies = listOf("Oui, avec un peu de sucre ☕")
        )
        assertEquals("bistro", scenario.id)
        assertFalse(scenario.suggestedReplies.isEmpty())
    }

    @Test
    fun testUserStreakEntityDefaults() {
        val streak = UserStreakEntity(
            id = 1,
            currentStreak = 7,
            longestStreak = 14,
            lastActiveDate = "2026-10-13",
            streakFreezeCount = 2,
            totalDaysActive = 21
        )
        assertEquals(7, streak.currentStreak)
        assertEquals(14, streak.longestStreak)
        assertEquals(2, streak.streakFreezeCount)
        assertEquals(21, streak.totalDaysActive)
    }

    @Test
    fun testStreakLogCreation() {
        val log = StreakLogEntity(
            id = 1,
            dateString = "2026-10-13",
            activityType = "AI_TUTOR_CHAT",
            xpEarned = 15,
            description = "Practiced café ordering with Camille"
        )
        assertEquals("AI_TUTOR_CHAT", log.activityType)
        assertEquals(15, log.xpEarned)
    }

    @Test
    fun testGlobalLanguageCatalogCoverage() {
        assertTrue("Global catalog should contain dozens of languages", LanguageCatalog.allLanguages.size >= 40)

        // Test Japanese course generation
        val jaLang = LanguageOption("JA", "JA N5", "🇯🇵", "Japanese")
        val jaCourse = LanguageCatalog.getCourseForLanguage(jaLang)
        assertEquals("Konnichiwa & Basics", jaCourse.unitTitle)
        assertTrue(jaCourse.scenarios.isNotEmpty())

        // Test Spanish course generation
        val esLang = LanguageOption("ES", "ES A1", "🇪🇸", "Spanish")
        val esCourse = LanguageCatalog.getCourseForLanguage(esLang)
        assertEquals("¡Hola! & Fundamentos", esCourse.unitTitle)
    }

    @Test
    fun testNigerianLanguagesSupport() {
        // Test Yoruba
        val yoLang = LanguageOption("YO", "YO A1", "🇳🇬", "Yoruba")
        val yoCourse = LanguageCatalog.getCourseForLanguage(yoLang)
        assertEquals("Ẹ n lẹ & Yorùbá Basics", yoCourse.unitTitle)
        assertEquals("Adebayo", yoCourse.tutorName)
        assertTrue(yoCourse.scenarios.isNotEmpty())
        assertTrue(yoCourse.reviewCards.any { it.french.contains("Báwo ni") })

        // Test Igbo
        val igLang = LanguageOption("IG", "IG A1", "🇳🇬", "Igbo")
        val igCourse = LanguageCatalog.getCourseForLanguage(igLang)
        assertEquals("Nnọọ & Asụsụ Igbo Basics", igCourse.unitTitle)
        assertEquals("Chidimma", igCourse.tutorName)
        assertTrue(igCourse.reviewCards.any { it.french.contains("Nnọọ") })

        // Test Hausa
        val haLang = LanguageOption("HA", "HA A1", "🇳🇬", "Hausa")
        val haCourse = LanguageCatalog.getCourseForLanguage(haLang)
        assertEquals("Sannu & Hausa Basics", haCourse.unitTitle)
        assertEquals("Aminu", haCourse.tutorName)

        // Test Nigerian Pidgin
        val pcmLang = LanguageOption("PCM", "PCM A1", "🇳🇬", "Nigerian Pidgin")
        val pcmCourse = LanguageCatalog.getCourseForLanguage(pcmLang)
        assertEquals("How Far & Naija Vibes", pcmCourse.unitTitle)
        assertEquals("Osas", pcmCourse.tutorName)

        // Test Dynamic Language Generation for Any Custom Language
        val customLang = LanguageOption("NV", "NV A1", "🌎", "Navajo")
        val customCourse = LanguageCatalog.getCourseForLanguage(customLang)
        assertEquals("Welcome to Navajo", customCourse.unitTitle)
        assertTrue(customCourse.quizQuestions.isNotEmpty())
    }

    @Test
    fun testAdMobConfigurationAndCompliance() {
        // Verify official Google AdMob test IDs are correctly configured
        assertEquals("ca-app-pub-3940256099942544~3347511713", com.example.ads.AdMobConfig.TEST_APP_ID)
        assertEquals("ca-app-pub-3940256099942544/6300978111", com.example.ads.AdMobConfig.TEST_BANNER_AD_UNIT_ID)
        assertTrue(com.example.ads.AdMobConfig.USE_TEST_ADS)
        assertEquals(com.example.ads.AdMobConfig.TEST_BANNER_AD_UNIT_ID, com.example.ads.AdMobConfig.bannerAdUnitId)

        // Verify Google Play Families & Child Safety Policies
        assertEquals(com.google.android.gms.ads.RequestConfiguration.MAX_AD_CONTENT_RATING_G, com.example.ads.AdMobConfig.maxAdContentRating)
        assertEquals(com.google.android.gms.ads.RequestConfiguration.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE, com.example.ads.AdMobConfig.isChildDirectedTreatment)
        assertEquals(com.google.android.gms.ads.RequestConfiguration.TAG_FOR_UNDER_AGE_OF_CONSENT_TRUE, com.example.ads.AdMobConfig.isUnderAgeOfConsent)

        // Verify Future Expansion Slots exist
        assertNotNull(com.example.ads.AdMobConfig.interstitialAdUnitId)
        assertNotNull(com.example.ads.AdMobConfig.rewardedAdUnitId)
        assertNotNull(com.example.ads.AdMobConfig.nativeAdUnitId)
    }

    @Test
    fun testMultiTopicCurriculumExpansion() {
        val frLang = LanguageOption("FR", "FR A1", "🇫🇷", "French")
        val frCourse = LanguageCatalog.getCourseForLanguage(frLang)

        // Verifies units extend far beyond basic greetings: food, dining, travel, shopping, numbers, social, health
        assertTrue("Course should have multiple units beyond basic greetings", frCourse.units.size >= 5)

        val unitTitles = frCourse.units.map { it.title }
        assertTrue("Should include food & dining unit", unitTitles.any { it.contains("Food", ignoreCase = true) || it.contains("Bistro", ignoreCase = true) })
        assertTrue("Should include transit & travel unit", unitTitles.any { it.contains("Travel", ignoreCase = true) || it.contains("Transit", ignoreCase = true) })
        assertTrue("Should include numbers & shopping unit", unitTitles.any { it.contains("Numbers", ignoreCase = true) || it.contains("Shopping", ignoreCase = true) })
        assertTrue("Should include health & emergency unit", unitTitles.any { it.contains("Health", ignoreCase = true) || it.contains("Pharmacy", ignoreCase = true) })
    }

    @Test
    fun testInteractiveExerciseTypes() {
        val frLang = LanguageOption("FR", "FR A1", "🇫🇷", "French")
        val units = CurriculumBuilder.buildUnitsForLanguage(frLang)
        val allExercises = units.flatMap { it.exercises }

        assertTrue("Curriculum should have diverse interactive exercises", allExercises.isNotEmpty())

        val exerciseTypes = allExercises.map { it.type }.toSet()
        assertTrue("Should support word scramble sentence builder", exerciseTypes.contains(ExerciseType.WORD_JUMBLE))
        assertTrue("Should support match pairs interactive game", exerciseTypes.contains(ExerciseType.MATCH_PAIRS))
        assertTrue("Should support audio listening comprehension", exerciseTypes.contains(ExerciseType.LISTENING))
        assertTrue("Should support speaking echo practice", exerciseTypes.contains(ExerciseType.SPEAKING_ECHO))

        // Check word jumble structure
        val jumble = allExercises.first { it.type == ExerciseType.WORD_JUMBLE }
        assertFalse(jumble.jumbledWords.isEmpty())
        assertFalse(jumble.correctWordSequence.isEmpty())
        assertNotNull(jumble.explanation)

        // Check pair matching structure
        val match = allExercises.first { it.type == ExerciseType.MATCH_PAIRS }
        assertTrue("Match pairs should have at least 4 items", match.pairItems.size >= 4)
    }
}

