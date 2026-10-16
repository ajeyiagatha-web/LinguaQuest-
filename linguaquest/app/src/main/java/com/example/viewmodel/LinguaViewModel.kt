package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.audio.TtsHelper
import com.example.data.local.LinguaDatabase
import com.example.data.local.SessionAccessManager
import com.example.data.local.StreakLogEntity
import com.example.data.local.UserStreakEntity
import com.example.data.repository.StreakRepository
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AppTab {
    LEARN,
    REVIEW,
    AI_TUTOR,
    LEADERBOARD,
    PROFILE
}

data class LinguaUiState(
    val currentTab: AppTab = AppTab.LEARN,
    val selectedLanguage: LanguageOption = LanguageOption("FR", "FR A1", "🇫🇷", "French"),
    val availableLanguages: List<LanguageOption> = LanguageCatalog.allLanguages,
    val tutorName: String = "Camille",
    val tutorCatchphrase: String = "Prêt pour pratiquer les salutations ?",
    val streaks: Int = 7,
    val longestStreak: Int = 14,
    val streakFreezeCount: Int = 2,
    val streakData: UserStreakEntity = UserStreakEntity(lastActiveDate = "2026-10-13"),
    val recentStreakLogs: List<StreakLogEntity> = emptyList(),
    val gems: Int = 420,
    val hearts: Int = 5,
    val maxHearts: Int = 5,
    val dailyXpCurrent: Int = 20,
    val dailyXpGoal: Int = 30,
    val unitProgressPercent: Int = 65,
    val unitTitle: String = "Bonjour & Basics",
    val unitSubtitle: String = "Master essential greetings and introductions",
    val pathNodes: List<PathNode> = listOf(
        PathNode("node_1", "Greetings", NodeType.LESSON, NodeState.COMPLETED, 0, hasCrown = true),
        PathNode("node_2", "Basic Phrases", NodeType.PHRASES, NodeState.COMPLETED, 48, hasCrown = true),
        PathNode("node_3", "Everyday Words", NodeType.LESSON, NodeState.ACTIVE, 0),
        PathNode("node_4", "Grammar Quiz", NodeType.QUIZ, NodeState.UNLOCKED, -48),
        PathNode("node_5", "Unit Chest", NodeType.CHEST, NodeState.LOCKED, 0)
    ),
    val currentScenario: Scenario = Scenario(
        id = "bistro",
        title = "At a Parisian Bistro",
        emoji = "🥐",
        mission = "Order a croissant and a café au lait naturally.",
        initialFrench = "Bonjour ! Bienvenue au Bistro des Étoiles. Qu'est-ce que je vous sers aujourd'hui ?",
        initialEnglish = "Hello! Welcome to Bistro des Étoiles. What can I get for you today?",
        suggestedReplies = listOf(
            "Oui, avec un peu de sucre ☕",
            "Non merci, sans sucre",
            "Combien ça coûte ? 💶",
            "L'addition, s'il vous plaît 💳"
        )
    ),
    val scenarios: List<Scenario> = emptyList(),
    val chatMessages: List<ChatMessage> = emptyList(),
    val activeGrammarTip: GrammarTip? = GrammarTip(
        title = "Smart Grammar Tip",
        tag = "Politeness",
        description = "You used \"Je voudrais\" instead of \"Je veux\" — perfect polite ordering in Paris!"
    ),
    val suggestedReplies: List<String> = listOf(
        "Oui, avec un peu de sucre ☕",
        "Non merci, sans sucre",
        "Combien ça coûte ? 💶"
    ),
    val reviewCards: List<ReviewCard> = emptyList(),
    val guidebookEntries: List<GuidebookEntry> = emptyList(),
    val activeQuizQuestions: List<QuizQuestion> = emptyList(),
    val isRecordingSpeech: Boolean = false,
    val recordingDurationSec: Int = 0,
    val isKeyboardMode: Boolean = false,
    val typedMessage: String = "",
    val showGuidebook: Boolean = false,
    val showLanguageSelector: Boolean = false,
    val showScenarioSelector: Boolean = false,
    val showStreakDetails: Boolean = false,
    val activeQuizNode: PathNode? = null,
    val isTtsSpeaking: Boolean = false,
    val toastMessage: String? = null,
    val freeLessonsCompleted: Int = 0,
    val maxFreeLessons: Int = 3,
    val hasLifetimeAccess: Boolean = false,
    val isAppLocked: Boolean = false,
    val enteredAccessKey: String = "",
    val accessKeyError: String? = null,
    val units: List<LearningUnit> = emptyList(),
    val selectedUnitIndex: Int = 0,
    val activeInteractiveExercises: List<InteractiveExercise> = emptyList()
)

class LinguaViewModel(application: Application) : AndroidViewModel(application) {
    private val ttsHelper = TtsHelper(application)
    private val database = LinguaDatabase.getDatabase(application)
    private val streakRepository = StreakRepository(database.streakDao())
    val sessionAccessManager = SessionAccessManager(application)

    private val _uiState = MutableStateFlow(LinguaUiState())
    val uiState: StateFlow<LinguaUiState> = _uiState.asStateFlow()

    init {
        // Initialize initial course (French) and persistent access state
        val initialCourse = LanguageCatalog.getCourseForLanguage(_uiState.value.selectedLanguage)
        val initialUnits = initialCourse.units.ifEmpty { CurriculumBuilder.buildUnitsForLanguage(_uiState.value.selectedLanguage) }
        val firstUnit = initialUnits.firstOrNull()
        val completed = sessionAccessManager.freeLessonsCompleted
        val lifetime = sessionAccessManager.hasLifetimeAccess
        val locked = sessionAccessManager.isLocked

        val combinedReview = (initialUnits.flatMap { u -> u.reviewCards } + initialCourse.reviewCards).distinctBy { card -> card.french }

        _uiState.update {
            it.copy(
                freeLessonsCompleted = completed,
                hasLifetimeAccess = lifetime,
                isAppLocked = locked,
                units = initialUnits,
                selectedUnitIndex = 0,
                unitTitle = firstUnit?.title ?: initialCourse.unitTitle,
                unitSubtitle = firstUnit?.subtitle ?: initialCourse.unitSubtitle,
                unitProgressPercent = firstUnit?.progressPercent ?: 65,
                pathNodes = firstUnit?.nodes ?: it.pathNodes,
                activeInteractiveExercises = firstUnit?.exercises ?: emptyList(),
                scenarios = initialCourse.scenarios,
                currentScenario = initialCourse.scenarios.first(),
                reviewCards = combinedReview,
                guidebookEntries = firstUnit?.guidebookEntries.orEmpty().ifEmpty { initialCourse.guidebookEntries },
                activeQuizQuestions = initialCourse.quizQuestions
            )
        }
        loadInitialMessages()
        initRoomStreakPersistence()
    }

    private fun initRoomStreakPersistence() {
        viewModelScope.launch {
            streakRepository.initializeIfEmpty()

            // Observe reactive Flow from Room database
            launch {
                streakRepository.streakFlow.collect { entity ->
                    entity?.let { streak ->
                        _uiState.update {
                            it.copy(
                                streaks = streak.currentStreak,
                                longestStreak = streak.longestStreak,
                                streakFreezeCount = streak.streakFreezeCount,
                                streakData = streak
                            )
                        }
                    }
                }
            }

            launch {
                streakRepository.recentLogsFlow.collect { logs ->
                    _uiState.update { it.copy(recentStreakLogs = logs) }
                }
            }
        }
    }

    private fun loadInitialMessages() {
        val initialMessages = listOf(
            ChatMessage(
                id = "msg_1",
                sender = MessageSender.AI,
                frenchText = "Bonjour ! Bienvenue au Bistro des Étoiles. Qu'est-ce que je vous sers aujourd'hui ?",
                englishText = "Hello! Welcome to Bistro des Étoiles. What can I get for you today?",
                timestamp = "Just now",
                isTranslationVisible = true
            ),
            ChatMessage(
                id = "msg_2",
                sender = MessageSender.USER,
                frenchText = "Bonjour ! Je voudrais un croissant et un café, s'il vous plaît.",
                timestamp = "Just now",
                isVoiceRecorded = true,
                nativeMatchPercentage = 96,
                xpEarned = 15
            ),
            ChatMessage(
                id = "msg_3",
                sender = MessageSender.AI,
                frenchText = "Parfait ! Avec du sucre pour votre café ?",
                englishText = "Perfect! With sugar for your coffee?",
                timestamp = "Listening",
                isNaturalContext = true,
                vocabHint = "Vocab: Sucre = Sugar",
                isTranslationVisible = false
            )
        )
        _uiState.update { it.copy(chatMessages = initialMessages) }
    }

    fun selectTab(tab: AppTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectLanguage(lang: LanguageOption) {
        val course = LanguageCatalog.getCourseForLanguage(lang)
        val initialScenario = course.scenarios.firstOrNull() ?: _uiState.value.currentScenario

        val initialMessage = ChatMessage(
            id = "init_lang_${System.currentTimeMillis()}",
            sender = MessageSender.AI,
            frenchText = initialScenario.initialFrench,
            englishText = initialScenario.initialEnglish,
            timestamp = "Just now",
            isTranslationVisible = true
        )

        val updatedNodes = listOf(
            PathNode("node_1", course.greetingNodeName, NodeType.LESSON, NodeState.COMPLETED, 0, hasCrown = true),
            PathNode("node_2", course.phrasesNodeName, NodeType.PHRASES, NodeState.COMPLETED, 48, hasCrown = true),
            PathNode("node_3", "Everyday Words", NodeType.LESSON, NodeState.ACTIVE, 0),
            PathNode("node_4", "Grammar Quiz", NodeType.QUIZ, NodeState.UNLOCKED, -48),
            PathNode("node_5", "Unit Chest", NodeType.CHEST, NodeState.LOCKED, 0)
        )

        val courseUnits = course.units.ifEmpty { CurriculumBuilder.buildUnitsForLanguage(lang) }
        val firstUnit = courseUnits.firstOrNull()
        val combinedReview = (courseUnits.flatMap { u -> u.reviewCards } + course.reviewCards).distinctBy { card -> card.french }

        _uiState.update {
            it.copy(
                selectedLanguage = lang,
                units = courseUnits,
                selectedUnitIndex = 0,
                unitTitle = firstUnit?.title ?: course.unitTitle,
                unitSubtitle = firstUnit?.subtitle ?: course.unitSubtitle,
                unitProgressPercent = firstUnit?.progressPercent ?: 65,
                tutorName = course.tutorName,
                tutorCatchphrase = course.tutorCatchphrase,
                pathNodes = firstUnit?.nodes ?: updatedNodes,
                activeInteractiveExercises = firstUnit?.exercises ?: emptyList(),
                scenarios = course.scenarios,
                currentScenario = initialScenario,
                chatMessages = listOf(initialMessage),
                suggestedReplies = initialScenario.suggestedReplies,
                reviewCards = combinedReview,
                guidebookEntries = firstUnit?.guidebookEntries.orEmpty().ifEmpty { course.guidebookEntries },
                activeQuizQuestions = course.quizQuestions,
                activeGrammarTip = null,
                showLanguageSelector = false,
                toastMessage = "Now learning ${lang.name} ${lang.flag}!"
            )
        }

        // Record course switch engagement in Room database
        viewModelScope.launch {
            streakRepository.recordEngagement(
                activityType = "COURSE_SWITCH",
                xpEarned = 10,
                description = "Started learning ${lang.name} (${lang.label})"
            )
        }

        // Pronounce initial greeting in the chosen language
        playAudio(initialScenario.initialFrench)
    }

    fun selectUnit(index: Int) {
        val units = _uiState.value.units
        if (index !in units.indices) return
        val unit = units[index]
        _uiState.update { state ->
            state.copy(
                selectedUnitIndex = index,
                unitTitle = unit.title,
                unitSubtitle = unit.subtitle,
                unitProgressPercent = unit.progressPercent,
                pathNodes = unit.nodes,
                activeInteractiveExercises = unit.exercises,
                guidebookEntries = unit.guidebookEntries.ifEmpty { state.guidebookEntries },
                reviewCards = if (unit.reviewCards.isNotEmpty()) unit.reviewCards else state.reviewCards,
                toastMessage = "Switched to Unit ${unit.unitNumber}: ${unit.title}"
            )
        }
    }

    fun addCustomLanguage(newLang: LanguageOption) {
        val currentList = _uiState.value.availableLanguages
        if (currentList.none { it.code.equals(newLang.code, ignoreCase = true) }) {
            _uiState.update {
                it.copy(availableLanguages = listOf(newLang) + it.availableLanguages)
            }
        }
        selectLanguage(newLang)
    }

    fun setLanguageSelectorVisible(visible: Boolean) {
        _uiState.update { it.copy(showLanguageSelector = visible) }
    }

    fun setScenarioSelectorVisible(visible: Boolean) {
        _uiState.update { it.copy(showScenarioSelector = visible) }
    }

    fun setGuidebookVisible(visible: Boolean) {
        _uiState.update { it.copy(showGuidebook = visible) }
    }

    fun setStreakDetailsVisible(visible: Boolean) {
        _uiState.update { it.copy(showStreakDetails = visible) }
    }

    fun extendStreakManual() {
        viewModelScope.launch {
            val newStreak = streakRepository.incrementStreakManual()
            _uiState.update {
                it.copy(
                    toastMessage = "🔥 Streak Extended to $newStreak Days! Saved to Room DB."
                )
            }
        }
    }

    fun addStreakFreeze() {
        viewModelScope.launch {
            streakRepository.addStreakFreeze()
            _uiState.update {
                it.copy(
                    toastMessage = "❄️ Streak Freeze equipped! Protected against missed days."
                )
            }
        }
    }

    fun selectScenario(scenario: Scenario) {
        val newInitialMessage = ChatMessage(
            id = "scenario_init_${System.currentTimeMillis()}",
            sender = MessageSender.AI,
            frenchText = scenario.initialFrench,
            englishText = scenario.initialEnglish,
            timestamp = "Just now",
            isTranslationVisible = true
        )
        _uiState.update {
            it.copy(
                currentScenario = scenario,
                chatMessages = listOf(newInitialMessage),
                suggestedReplies = scenario.suggestedReplies,
                showScenarioSelector = false,
                activeGrammarTip = null,
                toastMessage = "Scenario set: ${scenario.title}"
            )
        }
        playAudio(scenario.initialFrench)
    }

    fun toggleTranslation(messageId: String) {
        _uiState.update { state ->
            val updated = state.chatMessages.map { msg ->
                if (msg.id == messageId) {
                    msg.copy(isTranslationVisible = !msg.isTranslationVisible)
                } else msg
            }
            state.copy(chatMessages = updated)
        }
    }

    fun playAudio(text: String) {
        val langCode = _uiState.value.selectedLanguage.code
        ttsHelper.speak(text, langCode)
    }

    fun startSpeechRecording() {
        _uiState.update { it.copy(isRecordingSpeech = true, recordingDurationSec = 0) }
        viewModelScope.launch {
            for (i in 1..4) {
                delay(400)
                if (!_uiState.value.isRecordingSpeech) break
                _uiState.update { it.copy(recordingDurationSec = i) }
            }
        }
    }

    fun stopSpeechRecording(suggestedText: String? = null) {
        if (!_uiState.value.isRecordingSpeech) return
        _uiState.update { it.copy(isRecordingSpeech = false) }

        val speechResult = suggestedText ?: _uiState.value.suggestedReplies.firstOrNull() ?: "Thank you!"
        sendUserSpeech(speechResult)
    }

    fun sendUserSpeech(text: String) {
        val accuracy = (91..99).random()
        val xpGain = 15

        val userMessage = ChatMessage(
            id = "usr_${System.currentTimeMillis()}",
            sender = MessageSender.USER,
            frenchText = text,
            timestamp = "Just now",
            isVoiceRecorded = true,
            nativeMatchPercentage = accuracy,
            xpEarned = xpGain
        )

        val updatedXp = (_uiState.value.dailyXpCurrent + xpGain).coerceAtMost(_uiState.value.dailyXpGoal)
        val updatedGems = _uiState.value.gems + 5

        _uiState.update { state ->
            state.copy(
                chatMessages = state.chatMessages + userMessage,
                dailyXpCurrent = updatedXp,
                gems = updatedGems,
                toastMessage = "+$xpGain XP Earned! Streak Updated."
            )
        }

        // Record in Room Database
        viewModelScope.launch {
            streakRepository.recordEngagement(
                activityType = "AI_TUTOR_CHAT",
                xpEarned = xpGain,
                description = "Practiced ${_uiState.value.selectedLanguage.name} speaking dialogue"
            )
        }

        // Generate intelligent AI Tutor follow-up
        generateAiTutorResponse(text)
    }

    fun sendTypedMessage() {
        val text = _uiState.value.typedMessage.trim()
        if (text.isEmpty()) return
        _uiState.update { it.copy(typedMessage = "") }
        sendUserSpeech(text)
    }

    fun updateTypedMessage(text: String) {
        _uiState.update { it.copy(typedMessage = text) }
    }

    fun toggleKeyboardMode() {
        _uiState.update { it.copy(isKeyboardMode = !it.isKeyboardMode) }
    }

    private fun generateAiTutorResponse(userPrompt: String) {
        viewModelScope.launch {
            delay(1000)
            val langCode = _uiState.value.selectedLanguage.code.uppercase()
            val langName = _uiState.value.selectedLanguage.name

            val (aiTargetText, aiEnglish, vocabHint, tip) = when {
                langCode.startsWith("ES") -> {
                    Quad(
                        "¡Excelente elección! ¿Desea algo de postre o una tapa también?",
                        "Excellent choice! Would you like a dessert or a tapa as well?",
                        "Vocab: Tapa = Small appetizer / dish",
                        GrammarTip("Consejo de Español", "Cortesía", "Usar 'por favor' y 'gracias' siempre crea una conexión muy cálida en español.")
                    )
                }
                langCode == "JA" -> {
                    Quad(
                        "かしこまりました！他にご注文はございますか？",
                        "Certainly! Do you have any other orders?",
                        "Vocab: かしこまりました = Certainly / Understood",
                        GrammarTip("Japanese Politeness", "Keigo", "In Japan, store staff use polite honorific speech (Keigo) to show warm hospitality.")
                    )
                }
                langCode == "DE" -> {
                    Quad(
                        "Sehr gerne! Möchten Sie noch ein Gebäck oder Wasser dazu?",
                        "With pleasure! Would you like a pastry or water with that?",
                        "Vocab: Gebäck = Pastry",
                        GrammarTip("German Politeness", "Aussprache", "Achten Sie auf die Aussprache von 'bitte' und 'danke' für ein natürliches Auftreten.")
                    )
                }
                langCode == "IT" -> {
                    Quad(
                        "Certamente! Desidera anche un bicchiere d'acqua?",
                        "Certainly! Would you also like a glass of water?",
                        "Vocab: Desidera = Would you like",
                        GrammarTip("Consiglio Italiano", "Politezza", "'Vorrei' è sempre la scelta più elegante quando ordini in Italia.")
                    )
                }
                langCode == "ZH" -> {
                    Quad(
                        "好的，请稍等！请问还需要别的点心吗？",
                        "Great, please wait a moment! Would you like any other snacks?",
                        "Vocab: 点心 (Diǎnxin) = Snacks / Dim Sum",
                        GrammarTip("Mandarin Tip", "Tones", "Pay attention to the first tone in 'Qǐng' (please) for clear, polite speech.")
                    )
                }
                langCode == "KO" -> {
                    Quad(
                        "네, 알겠습니다! 디저트도 같이 준비해 드릴까요?",
                        "Yes, understood! Shall I prepare dessert together?",
                        "Vocab: 디저트 (Dijeoteu) = Dessert",
                        GrammarTip("Korean Tip", "Politeness", "Ending sentences with '-yo' or '-juseyo' keeps interactions friendly and respectful.")
                    )
                }
                langCode.startsWith("PT") -> {
                    Quad(
                        "Com certeza! Deseja mais alguma coisa?",
                        "Certainly! Would you like anything else?",
                        "Vocab: Deseja = Would you like",
                        GrammarTip("Dica de Português", "Cortesia", "Dizer 'por favor' e 'obrigado' torna qualquer pedido natural e amigável.")
                    )
                }
                langCode == "AR" -> {
                    Quad(
                        "بكل سرور! هل تود أي شيء آخر؟",
                        "With pleasure! Would you like anything else?",
                        "Vocab: بكل سرور = With pleasure",
                        GrammarTip("Arabic Tip", "Hospitality", "Using 'Min fadlik' (please) is standard polite etiquette across the Arab world.")
                    )
                }
                langCode == "RU" -> {
                    Quad(
                        "Конечно! Желаете что-нибудь ещё к заказу?",
                        "Of course! Would you like anything else with your order?",
                        "Vocab: Конечно = Of course",
                        GrammarTip("Russian Tip", "Etiquette", "Saying 'Пожалуйста' shows great politeness when requesting or replying.")
                    )
                }
                langCode == "HI" -> {
                    Quad(
                        "बिल्कुल! क्या आप कुछ मीठा या नाश्ता भी लेना चाहेंगे?",
                        "Certainly! Would you like to have some sweets or snacks too?",
                        "Vocab: बिल्कुल = Certainly / Absolutely",
                        GrammarTip("Hindi Tip", "Respect", "Using 'Kripaya' (please) adds warm politeness in conversations.")
                    )
                }
                langCode == "FR" && userPrompt.contains("sucre", ignoreCase = true) -> {
                    Quad(
                        "Très bien ! Et avec ceci, vous désirez une viennoiserie ou autre chose ?",
                        "Very good! And with this, would you like a pastry or anything else?",
                        "Vocab: Viennoiserie = French pastry",
                        GrammarTip("Smart Grammar Tip", "Ordering", "In France, 'Et avec ceci ?' is the standard way baristas ask 'Anything else?'")
                    )
                }
                else -> {
                    Quad(
                        "Excellent! You are doing great in $langName. Would you like to practice more?",
                        "Excellent! You are doing great in $langName. Would you like to practice more?",
                        "Vocab: $langName Fluency",
                        GrammarTip("Polyglot Tip", "Fluency", "Every response you practice builds muscle memory for native $langName speaking!")
                    )
                }
            }

            val aiMessage = ChatMessage(
                id = "ai_${System.currentTimeMillis()}",
                sender = MessageSender.AI,
                frenchText = aiTargetText,
                englishText = aiEnglish,
                timestamp = "Just now",
                isNaturalContext = true,
                vocabHint = vocabHint,
                isTranslationVisible = false
            )

            val newReplies = when (langCode) {
                "ES", "ES_MX" -> listOf("Sí, por favor ☕", "No, gracias, eso es todo", "¿La cuenta, por favor? 💳")
                "JA" -> listOf("はい、お願いします 🍵", "いいえ、大丈夫です", "お会計をお願いします 💳")
                "DE" -> listOf("Ja, gerne ☕", "Nein danke, das ist alles", "Die Rechnung bitte 💳")
                "IT" -> listOf("Sì, per favore ☕", "No grazie, va bene così", "Il conto, per favore 💳")
                "ZH" -> listOf("好的，谢谢 🫖", "不用了，谢谢", "买单，谢谢 💳")
                "KO" -> listOf("네, 좋아요 ☕", "괜찮습니다, 감사합니다", "계산해 주세요 💳")
                "PT", "PT_BR" -> listOf("Sim, por favor ☕", "Não, obrigado, só isso", "A conta, por favor 💳")
                else -> listOf("Yes, please!", "No, thank you, that is all", "The check, please 💳")
            }

            _uiState.update { state ->
                state.copy(
                    chatMessages = state.chatMessages + aiMessage,
                    activeGrammarTip = tip,
                    suggestedReplies = newReplies
                )
            }
            playAudio(aiTargetText)
        }
    }

    fun openQuizForNode(node: PathNode) {
        if (_uiState.value.isAppLocked) {
            return
        }
        if (node.state == NodeState.LOCKED) {
            _uiState.update { it.copy(toastMessage = "Complete previous lessons to unlock this chest!") }
            return
        }
        _uiState.update { it.copy(activeQuizNode = node) }
    }

    fun closeQuiz() {
        _uiState.update { it.copy(activeQuizNode = null) }
    }

    fun completeQuiz(earnedXp: Int) {
        val currentNode = _uiState.value.activeQuizNode
        val currentNodeId = currentNode?.id
        val updatedNodes = _uiState.value.pathNodes.mapIndexed { index, node ->
            if (node.id == currentNodeId) {
                node.copy(state = NodeState.COMPLETED, hasCrown = true)
            } else if (index > 0 && _uiState.value.pathNodes[index - 1].id == currentNodeId && node.state == NodeState.LOCKED) {
                node.copy(state = NodeState.ACTIVE)
            } else node
        }

        val updatedProgress = (_uiState.value.unitProgressPercent + 15).coerceAtMost(100)
        val updatedDailyXp = (_uiState.value.dailyXpCurrent + earnedXp).coerceAtMost(_uiState.value.dailyXpGoal)
        val updatedGems = _uiState.value.gems + 10

        // Increment the session counter
        val updatedCompletedCount = sessionAccessManager.incrementCompletedLessons()
        val isLocked = sessionAccessManager.isLocked

        val updatedUnits = _uiState.value.units.mapIndexed { idx, u ->
            if (idx == _uiState.value.selectedUnitIndex) {
                u.copy(nodes = updatedNodes, progressPercent = updatedProgress)
            } else u
        }

        _uiState.update { state ->
            state.copy(
                pathNodes = updatedNodes,
                units = updatedUnits,
                unitProgressPercent = updatedProgress,
                dailyXpCurrent = updatedDailyXp,
                gems = updatedGems,
                activeQuizNode = null,
                freeLessonsCompleted = updatedCompletedCount,
                isAppLocked = isLocked,
                toastMessage = if (isLocked) {
                    "You have used your 3 free lessons!"
                } else {
                    "Lesson Complete ($updatedCompletedCount/3 free used)! +$earnedXp XP"
                }
            )
        }

        // Save progress to Room database
        viewModelScope.launch {
            streakRepository.recordEngagement(
                activityType = "LESSON_COMPLETED",
                xpEarned = earnedXp,
                description = "Completed '${currentNode?.title ?: "Lesson"}' in ${_uiState.value.selectedLanguage.name}"
            )
        }
    }

    /**
     * Updates the entered access key and automatically unlocks if the key matches 'AIPro999'.
     */
    fun updateAccessKeyInput(key: String) {
        _uiState.update { it.copy(enteredAccessKey = key, accessKeyError = null) }
        // "If the user types the key 'AIPro999', hide the lock screen and grant them permanent, unlimited access."
        if (key.trim() == SessionAccessManager.VALID_ACCESS_KEY) {
            unlockLifetimeAccess()
        }
    }

    /**
     * Validates the currently entered access key and grants lifetime access if valid.
     */
    fun unlockLifetimeAccess(): Boolean {
        val key = _uiState.value.enteredAccessKey
        val success = sessionAccessManager.validateAndUnlock(key)
        if (success) {
            _uiState.update {
                it.copy(
                    hasLifetimeAccess = true,
                    isAppLocked = false,
                    accessKeyError = null,
                    toastMessage = "Unlimited Lifetime Access Unlocked! 🎉"
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    accessKeyError = "Invalid key '${key.trim()}'. Enter 'AIPro999' or purchase an access key."
                )
            }
        }
        return success
    }

    /**
     * Resets the session counter for testing and development.
     */
    fun resetAccessForDebug() {
        sessionAccessManager.resetForTesting()
        _uiState.update {
            it.copy(
                freeLessonsCompleted = 0,
                hasLifetimeAccess = false,
                isAppLocked = false,
                enteredAccessKey = "",
                accessKeyError = null,
                toastMessage = "Session counter reset to 0/3 free lessons."
            )
        }
    }

    fun recordReviewActivity(xp: Int = 10) {
        viewModelScope.launch {
            streakRepository.recordEngagement(
                activityType = "DAILY_PRACTICE",
                xpEarned = xp,
                description = "Reviewed ${_uiState.value.selectedLanguage.name} flashcards"
            )
        }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    override fun onCleared() {
        super.onCleared()
        ttsHelper.shutdown()
    }

    private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
}
