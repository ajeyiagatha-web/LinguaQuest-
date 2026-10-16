package com.example.model

enum class NodeState {
    COMPLETED,
    ACTIVE,
    UNLOCKED,
    LOCKED
}

enum class NodeType {
    LESSON,
    PHRASES,
    QUIZ,
    CHEST
}

data class PathNode(
    val id: String,
    val title: String,
    val type: NodeType,
    val state: NodeState,
    val xOffsetDp: Int,
    val hasCrown: Boolean = false
)

enum class MessageSender {
    AI,
    USER
}

data class GrammarTip(
    val title: String = "Smart Grammar Tip",
    val tag: String = "Politeness",
    val description: String
)

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val frenchText: String,
    val englishText: String = "",
    val timestamp: String = "Just now",
    val isVoiceRecorded: Boolean = false,
    val nativeMatchPercentage: Int? = null,
    val xpEarned: Int? = null,
    val isNaturalContext: Boolean = false,
    val vocabHint: String? = null,
    val grammarTip: GrammarTip? = null,
    val isTranslationVisible: Boolean = false
)

data class Scenario(
    val id: String,
    val title: String,
    val emoji: String,
    val mission: String,
    val initialFrench: String,
    val initialEnglish: String,
    val suggestedReplies: List<String>
)

data class LanguageOption(
    val code: String,
    val label: String,
    val flag: String,
    val name: String
)

data class QuizQuestion(
    val prompt: String,
    val frenchText: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
)

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val avatarUrl: String,
    val xp: Int,
    val flag: String,
    val isCurrentUser: Boolean = false
)

data class ReviewCard(
    val french: String,
    val english: String,
    val pronunciation: String,
    val exampleFrench: String,
    val exampleEnglish: String,
    val category: String
)

enum class ExerciseType {
    WORD_JUMBLE,     // Sentence construction: tap words in correct order
    MATCH_PAIRS,     // Tap matching pairs between two columns
    LISTENING,       // Audio dictation / listening comprehension with turtle speed
    FILL_IN_BLANK,   // Sentence with blank and multiple choice chips
    SPEAKING_ECHO,   // Listen and speak into mic for pronunciation score
    MULTIPLE_CHOICE  // Smart multiple choice with explanation
}

data class PairMatchItem(
    val id: String,
    val targetWord: String,
    val englishWord: String
)

data class InteractiveExercise(
    val id: String,
    val type: ExerciseType,
    val prompt: String,
    val targetText: String = "",
    val englishText: String = "",
    val pronunciation: String = "",
    val audioSpeedAvailable: Boolean = true,
    // For WORD_JUMBLE:
    val jumbledWords: List<String> = emptyList(),
    val correctWordSequence: List<String> = emptyList(),
    // For MATCH_PAIRS:
    val pairItems: List<PairMatchItem> = emptyList(),
    // For MULTIPLE_CHOICE or FILL_IN_BLANK:
    val options: List<String> = emptyList(),
    val correctOptionIndex: Int = 0,
    val blankSentence: String = "",
    val explanation: String = "",
    val cultureTip: String = ""
)

data class LearningUnit(
    val id: String,
    val unitNumber: Int,
    val title: String,
    val subtitle: String,
    val emoji: String,
    val themeColorHex: Long = 0xFF58CC02, // Duolingo style vibrant colors
    val nodes: List<PathNode>,
    val exercises: List<InteractiveExercise> = emptyList(),
    val guidebookEntries: List<GuidebookEntry> = emptyList(),
    val reviewCards: List<ReviewCard> = emptyList(),
    val progressPercent: Int = 0
)

