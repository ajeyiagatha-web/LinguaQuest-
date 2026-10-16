package com.example

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ads.AdMobManager
import com.example.ui.components.*
import com.example.ui.screens.*
import com.example.ui.theme.LinguaQuestTheme
import com.example.ui.theme.SurfaceBackground
import com.example.viewmodel.AppTab
import com.example.viewmodel.LinguaViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: LinguaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Google Mobile Ads SDK with child-directed treatment & G-rating
        AdMobManager.initialize(applicationContext)

        setContent {
            LinguaQuestTheme {
                val state by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                // Show toast when message arrives
                LaunchedEffect(state.toastMessage) {
                    state.toastMessage?.let { msg ->
                        Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
                        viewModel.clearToast()
                    }
                }

                // Handle system back navigation
                if (state.currentTab != AppTab.LEARN && !state.isAppLocked) {
                    BackHandler {
                        viewModel.selectTab(AppTab.LEARN)
                    }
                }

                Box(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        // The Learn screen displays the top app bar with stats.
                        // On AI Tutor screen, a dedicated tutor header is shown inside the screen itself.
                        if (state.currentTab != AppTab.AI_TUTOR) {
                            LinguaTopAppBar(
                                selectedLanguage = state.selectedLanguage,
                                streaks = state.streaks,
                                gems = state.gems,
                                hearts = state.hearts,
                                onLanguageClick = { viewModel.setLanguageSelectorVisible(true) },
                                onStreakClick = {
                                    viewModel.setStreakDetailsVisible(true)
                                }
                            )
                        }
                    },
                    bottomBar = {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(SurfaceBackground)
                        ) {
                            // Non-disruptive, compliant AdMob Banner
                            // Never shown during active AI speech sessions or lesson quizzes, or for lifetime members
                            if (state.currentTab != AppTab.AI_TUTOR && state.activeQuizNode == null && !state.hasLifetimeAccess) {
                                AdMobBanner(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 8.dp)
                                )
                            }
                            LinguaBottomNavBar(
                                currentTab = state.currentTab,
                                onTabSelected = { tab -> viewModel.selectTab(tab) }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(SurfaceBackground)
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (state.currentTab) {
                            AppTab.LEARN -> {
                                LearnScreen(
                                    state = state,
                                    onNodeClick = { node -> viewModel.openQuizForNode(node) },
                                    onGuidebookClick = { viewModel.setGuidebookVisible(true) },
                                    onChatNowClick = { viewModel.selectTab(AppTab.AI_TUTOR) },
                                    onStreakClick = { viewModel.setStreakDetailsVisible(true) },
                                    onSelectUnit = { unitIndex -> viewModel.selectUnit(unitIndex) }
                                )
                            }
                            AppTab.AI_TUTOR -> {
                                AiTutorScreen(
                                    state = state,
                                    onBackClick = { viewModel.selectTab(AppTab.LEARN) },
                                    onChangeScenarioClick = { viewModel.setScenarioSelectorVisible(true) },
                                    onPlayAudio = { text -> viewModel.playAudio(text) },
                                    onToggleTranslation = { msgId -> viewModel.toggleTranslation(msgId) },
                                    onSuggestedReplyClick = { reply -> viewModel.sendUserSpeech(reply) },
                                    onStartRecording = { viewModel.startSpeechRecording() },
                                    onStopRecording = { viewModel.stopSpeechRecording() },
                                    onToggleKeyboard = { viewModel.toggleKeyboardMode() },
                                    onTypedMessageChange = { text -> viewModel.updateTypedMessage(text) },
                                    onSendTypedMessage = { viewModel.sendTypedMessage() },
                                    onHelpClick = {
                                        Toast.makeText(this@MainActivity, "💡 Tip: Say 'Je voudrais...' or pick a suggestion!", Toast.LENGTH_LONG).show()
                                    }
                                )
                            }
                            AppTab.REVIEW -> {
                                ReviewScreen(
                                    customCards = state.reviewCards,
                                    languageName = state.selectedLanguage.name,
                                    onPlayAudio = { text ->
                                        viewModel.playAudio(text)
                                        viewModel.recordReviewActivity(10)
                                    }
                                )
                            }
                            AppTab.LEADERBOARD -> {
                                LeaderboardScreen(
                                    userCurrentXp = state.dailyXpCurrent
                                )
                            }
                            AppTab.PROFILE -> {
                                ProfileScreen(
                                    state = state,
                                    onLanguageClick = { viewModel.setLanguageSelectorVisible(true) },
                                    onStreakClick = { viewModel.setStreakDetailsVisible(true) },
                                    onResetAccessClick = { viewModel.resetAccessForDebug() }
                                )
                            }
                        }

                        // Modal Sheets & Dialogs
                        if (state.showStreakDetails) {
                            StreakDetailsSheet(
                                streakData = state.streakData,
                                recentLogs = state.recentStreakLogs,
                                onExtendStreakClick = { viewModel.extendStreakManual() },
                                onAddFreezeClick = { viewModel.addStreakFreeze() },
                                onDismiss = { viewModel.setStreakDetailsVisible(false) }
                            )
                        }

                        if (state.showGuidebook) {
                            GuidebookSheet(
                                unitTitle = state.unitTitle,
                                entries = state.guidebookEntries,
                                onDismiss = { viewModel.setGuidebookVisible(false) },
                                onPlayAudio = { text -> viewModel.playAudio(text) }
                            )
                        }

                        if (state.showLanguageSelector) {
                            LanguageSelectorSheet(
                                languages = state.availableLanguages,
                                selectedLanguage = state.selectedLanguage,
                                onSelectLanguage = { lang -> viewModel.selectLanguage(lang) },
                                onAddCustomLanguage = { newLang -> viewModel.addCustomLanguage(newLang) },
                                onDismiss = { viewModel.setLanguageSelectorVisible(false) }
                            )
                        }

                        if (state.showScenarioSelector) {
                            ScenarioSelectorSheet(
                                scenarios = state.scenarios,
                                currentScenario = state.currentScenario,
                                onSelectScenario = { scenario -> viewModel.selectScenario(scenario) },
                                onDismiss = { viewModel.setScenarioSelectorVisible(false) }
                            )
                        }

                        state.activeQuizNode?.let { node ->
                            LessonExerciseDialog(
                                node = node,
                                customQuestions = state.activeQuizQuestions,
                                interactiveExercises = state.activeInteractiveExercises,
                                languageCode = state.selectedLanguage.code,
                                languageName = state.selectedLanguage.name,
                                onDismiss = { viewModel.closeQuiz() },
                                onComplete = { earnedXp -> viewModel.completeQuiz(earnedXp) },
                                onPlayAudio = { text -> viewModel.playAudio(text) }
                            )
                        }
                    }
                }

                // Permanent Lock Screen: Covers the app as soon as user completes 3 free lessons
                if (state.isAppLocked) {
                    LifetimeLockScreen(
                        enteredKey = state.enteredAccessKey,
                        errorMessage = state.accessKeyError,
                        onKeyChanged = { viewModel.updateAccessKeyInput(it) },
                        onUnlock = { viewModel.unlockLifetimeAccess() }
                    )
                }
            }
        }
    }
}
}
