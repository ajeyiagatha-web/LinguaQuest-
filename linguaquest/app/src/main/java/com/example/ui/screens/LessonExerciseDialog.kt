package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.*
import com.example.ui.theme.*

@Composable
fun LessonExerciseDialog(
    node: PathNode,
    customQuestions: List<QuizQuestion> = emptyList(),
    interactiveExercises: List<InteractiveExercise> = emptyList(),
    languageCode: String = "FR",
    languageName: String = "French",
    onDismiss: () -> Unit,
    onComplete: (Int) -> Unit,
    onPlayAudio: (String) -> Unit
) {
    // Choose interactive exercises if available, else convert questions to exercises
    val exercises: List<InteractiveExercise> = remember(interactiveExercises, customQuestions) {
        if (interactiveExercises.isNotEmpty()) {
            interactiveExercises
        } else if (customQuestions.isNotEmpty()) {
            customQuestions.mapIndexed { idx, q ->
                InteractiveExercise(
                    id = "q_$idx",
                    type = ExerciseType.MULTIPLE_CHOICE,
                    prompt = q.prompt,
                    targetText = q.frenchText,
                    options = q.options,
                    correctOptionIndex = q.correctIndex,
                    explanation = q.explanation
                )
            }
        } else {
            // Default fallback interactive exercises
            listOf(
                InteractiveExercise(
                    id = "def_1",
                    type = ExerciseType.WORD_JUMBLE,
                    prompt = "Build the polite ordering sentence:",
                    englishText = "I would like a coffee, please.",
                    targetText = "Je voudrais un café s'il vous plaît",
                    jumbledWords = listOf("s'il vous plaît", "un café", "Je voudrais"),
                    correctWordSequence = listOf("Je voudrais", "un café", "s'il vous plaît"),
                    explanation = "'Je voudrais' is polite and standard."
                ),
                InteractiveExercise(
                    id = "def_2",
                    type = ExerciseType.MATCH_PAIRS,
                    prompt = "Match the phrases:",
                    pairItems = listOf(
                        PairMatchItem("1", "Bonjour", "Hello"),
                        PairMatchItem("2", "Merci", "Thank you"),
                        PairMatchItem("3", "S'il vous plaît", "Please"),
                        PairMatchItem("4", "Au revoir", "Goodbye")
                    )
                )
            )
        }
    }

    var currentIndex by remember { mutableStateOf(0) }
    var isLessonCompleted by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(SurfaceBackground),
            color = SurfaceBackground
        ) {
            if (isLessonCompleted) {
                // Celebration Completion Screen
                LessonCelebrationView(
                    nodeTitle = node.title,
                    languageName = languageName,
                    onContinue = { onComplete(30) }
                )
            } else {
                val currentExercise = exercises[currentIndex]

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Header with Progress Bar and Close Button
                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            IconButton(onClick = onDismiss) {
                                Icon(imageVector = Icons.Default.Close, contentDescription = "Exit Lesson")
                            }

                            // Progress Bar
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(12.dp)
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(SurfaceContainerHighest)
                                    .padding(2.dp)
                            ) {
                                val fraction = ((currentIndex + 1).toFloat() / exercises.size.toFloat()).coerceIn(0f, 1f)
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(fraction = fraction)
                                        .clip(RoundedCornerShape(9999.dp))
                                        .background(BrandSecondary)
                                )
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = "${currentIndex + 1}/${exercises.size}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BrandSecondary
                                )
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            val typeBadge = when (currentExercise.type) {
                                ExerciseType.WORD_JUMBLE -> "🔤 Sentence Builder"
                                ExerciseType.MATCH_PAIRS -> "⚡ Match Pairs"
                                ExerciseType.LISTENING -> "🎧 Listening"
                                ExerciseType.SPEAKING_ECHO -> "🎙️ Speaking Echo"
                                ExerciseType.FILL_IN_BLANK -> "✏️ Fill in Blank"
                                ExerciseType.MULTIPLE_CHOICE -> "💡 Quick Quiz"
                            }
                            AssistChip(
                                onClick = {},
                                label = { Text(typeBadge, style = MaterialTheme.typography.labelSmall) }
                            )
                            Text(
                                text = node.title,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextOnSurface
                                )
                            )
                        }
                    }

                    // Interactive Exercise Body (Switches by exercise type)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
                    ) {
                        key(currentExercise.id) {
                            when (currentExercise.type) {
                                ExerciseType.WORD_JUMBLE -> {
                                    InteractiveWordJumbleView(
                                        exercise = currentExercise,
                                        onPlayAudio = onPlayAudio,
                                        onNext = {
                                            if (currentIndex < exercises.size - 1) {
                                                currentIndex++
                                            } else {
                                                isLessonCompleted = true
                                            }
                                        }
                                    )
                                }
                                ExerciseType.MATCH_PAIRS -> {
                                    InteractiveMatchPairsView(
                                        exercise = currentExercise,
                                        onPlayAudio = onPlayAudio,
                                        onNext = {
                                            if (currentIndex < exercises.size - 1) {
                                                currentIndex++
                                            } else {
                                                isLessonCompleted = true
                                            }
                                        }
                                    )
                                }
                                ExerciseType.LISTENING -> {
                                    InteractiveListeningView(
                                        exercise = currentExercise,
                                        onPlayAudio = onPlayAudio,
                                        onNext = {
                                            if (currentIndex < exercises.size - 1) {
                                                currentIndex++
                                            } else {
                                                isLessonCompleted = true
                                            }
                                        }
                                    )
                                }
                                ExerciseType.SPEAKING_ECHO -> {
                                    InteractiveSpeakingEchoView(
                                        exercise = currentExercise,
                                        onPlayAudio = onPlayAudio,
                                        onNext = {
                                            if (currentIndex < exercises.size - 1) {
                                                currentIndex++
                                            } else {
                                                isLessonCompleted = true
                                            }
                                        }
                                    )
                                }
                                ExerciseType.FILL_IN_BLANK,
                                ExerciseType.MULTIPLE_CHOICE -> {
                                    InteractiveMultipleChoiceView(
                                        exercise = currentExercise,
                                        onPlayAudio = onPlayAudio,
                                        onNext = {
                                            if (currentIndex < exercises.size - 1) {
                                                currentIndex++
                                            } else {
                                                isLessonCompleted = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------------
// 1. INTERACTIVE SENTENCE BUILDER (WORD JUMBLE)
// --------------------------------------------------------------------------
@Composable
fun InteractiveWordJumbleView(
    exercise: InteractiveExercise,
    onPlayAudio: (String) -> Unit,
    onNext: () -> Unit
) {
    val availableWords = remember(exercise.id) {
        mutableStateListOf<String>().apply { addAll(exercise.jumbledWords.ifEmpty { exercise.correctWordSequence.shuffled() }) }
    }
    val placedWords = remember(exercise.id) { mutableStateListOf<String>() }
    var isChecked by remember { mutableStateOf(false) }
    var isCorrect by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = exercise.prompt,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextOnSurface
                )
            )

            // Prompt Card
            Card(
                colors = CardDefaults.cardColors(containerColor = BrandPrimaryFixed.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(BrandPrimaryContainer)
                            .clickable { onPlayAudio(exercise.targetText) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Play Audio", tint = Color.White)
                    }
                    Column {
                        Text(
                            text = exercise.englishText,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                        )
                        if (exercise.pronunciation.isNotEmpty()) {
                            Text(
                                text = exercise.pronunciation,
                                style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant)
                            )
                        }
                    }
                }
            }

            // Placed Words Sentence Area (Active Construction Box)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 90.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(SurfaceContainerLowest)
                    .border(
                        width = 2.dp,
                        color = when {
                            isChecked && isCorrect -> BrandSecondary
                            isChecked && !isCorrect -> MaterialTheme.colorScheme.error
                            placedWords.isNotEmpty() -> BrandPrimary
                            else -> OutlineVariantColor
                        },
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(12.dp)
            ) {
                if (placedWords.isEmpty()) {
                    Text(
                        text = "Tap words below in order to assemble the sentence...",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextOnSurfaceVariant),
                        modifier = Modifier.align(Alignment.Center)
                    )
                } else {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        placedWords.forEachIndexed { index, word ->
                            AssistChip(
                                onClick = {
                                    if (!isChecked) {
                                        placedWords.removeAt(index)
                                        availableWords.add(word)
                                    }
                                },
                                label = { Text(word, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)) },
                                colors = AssistChipDefaults.assistChipColors(containerColor = BrandPrimaryFixedDim.copy(alpha = 0.5f))
                            )
                        }
                    }
                }
            }

            // Bank of Available Word Chips
            Text(
                text = "Available words:",
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = TextOnSurfaceVariant)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                availableWords.forEachIndexed { index, word ->
                    ElevatedFilterChip(
                        selected = false,
                        onClick = {
                            if (!isChecked) {
                                availableWords.removeAt(index)
                                placedWords.add(word)
                                onPlayAudio(word)
                            }
                        },
                        label = {
                            Text(
                                text = word,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold)
                            )
                        }
                    )
                }
            }
        }

        // Action & Validation
        Column(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (isChecked) {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isCorrect) BrandSecondaryContainer.copy(alpha = 0.6f) else MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.6f)
                    ),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Icon(
                                imageVector = if (isCorrect) Icons.Default.CheckCircle else Icons.Default.Error,
                                contentDescription = null,
                                tint = if (isCorrect) BrandSecondary else MaterialTheme.colorScheme.error
                            )
                            Text(
                                text = if (isCorrect) "Fantastic! +15 XP" else "Correct phrase: ${exercise.correctWordSequence.joinToString(" ")}",
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        if (exercise.explanation.isNotEmpty()) {
                            Text(
                                text = exercise.explanation,
                                style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant),
                                modifier = Modifier.padding(top = 4.dp)
                            )
                        }
                    }
                }
            }

            Button(
                onClick = {
                    if (!isChecked) {
                        val currentBuilt = placedWords.joinToString(" ").trim().lowercase().replace("?", "").replace("!", "").replace(".", "")
                        val expected = exercise.correctWordSequence.joinToString(" ").trim().lowercase().replace("?", "").replace("!", "").replace(".", "")
                        isCorrect = currentBuilt == expected || placedWords == exercise.correctWordSequence
                        isChecked = true
                    } else {
                        onNext()
                    }
                },
                enabled = placedWords.isNotEmpty(),
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isChecked && isCorrect) BrandSecondary else BrandPrimary
                )
            ) {
                Text(
                    text = if (!isChecked) "CHECK ANSWER" else "CONTINUE",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold)
                )
            }
        }
    }
}

// --------------------------------------------------------------------------
// 2. INTERACTIVE MATCH THE PAIRS (SPEED MATCH / MEMORY GAME)
// --------------------------------------------------------------------------
@Composable
fun InteractiveMatchPairsView(
    exercise: InteractiveExercise,
    onPlayAudio: (String) -> Unit,
    onNext: () -> Unit
) {
    val items = exercise.pairItems
    val targetWords = remember(exercise.id) { items.map { it.targetWord } }
    val englishWords = remember(exercise.id) { items.map { it.englishWord }.shuffled() }

    var selectedTarget by remember { mutableStateOf<String?>(null) }
    var selectedEnglish by remember { mutableStateOf<String?>(null) }
    val matchedPairs = remember { mutableStateListOf<String>() } // Stores matched target words
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = exercise.prompt,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextOnSurface
                )
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Matched: ${matchedPairs.size} / ${items.size}",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = BrandSecondary,
                        fontWeight = FontWeight.Bold
                    )
                )
                if (matchedPairs.size == items.size) {
                    Text(
                        text = "All Matched! 🎉",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = BrandSecondary,
                            fontWeight = FontWeight.ExtraBold
                        )
                    )
                }
            }

            // Columns of cards
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Column 1: Target Language
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    targetWords.forEach { word ->
                        val isMatched = matchedPairs.contains(word)
                        val isSelected = selectedTarget == word

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    when {
                                        isMatched -> BrandSecondaryContainer.copy(alpha = 0.4f)
                                        isSelected -> BrandPrimaryFixed.copy(alpha = 0.6f)
                                        else -> SurfaceContainerLowest
                                    }
                                )
                                .border(
                                    width = if (isSelected || isMatched) 2.dp else 1.dp,
                                    color = when {
                                        isMatched -> BrandSecondary
                                        isSelected -> BrandPrimary
                                        else -> OutlineVariantColor
                                    },
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable(enabled = !isMatched) {
                                    selectedTarget = word
                                    errorMessage = null
                                    onPlayAudio(word)

                                    // Check match if English word is already chosen
                                    selectedEnglish?.let { eng ->
                                        val match = items.find { it.targetWord == word && it.englishWord == eng }
                                        if (match != null) {
                                            matchedPairs.add(word)
                                            selectedTarget = null
                                            selectedEnglish = null
                                        } else {
                                            errorMessage = "Try again!"
                                            selectedTarget = null
                                            selectedEnglish = null
                                        }
                                    }
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                if (isMatched) {
                                    Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = BrandSecondary, modifier = Modifier.size(18.dp))
                                }
                                Text(
                                    text = word,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = if (isSelected || isMatched) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isMatched) BrandSecondary else TextOnSurface
                                    ),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }

                // Column 2: English translations
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    englishWords.forEach { eng ->
                        val matchingItem = items.find { it.englishWord == eng }
                        val isMatched = matchingItem != null && matchedPairs.contains(matchingItem.targetWord)
                        val isSelected = selectedEnglish == eng

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(60.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(
                                    when {
                                        isMatched -> BrandSecondaryContainer.copy(alpha = 0.4f)
                                        isSelected -> BrandPrimaryFixed.copy(alpha = 0.6f)
                                        else -> SurfaceContainerLowest
                                    }
                                )
                                .border(
                                    width = if (isSelected || isMatched) 2.dp else 1.dp,
                                    color = when {
                                        isMatched -> BrandSecondary
                                        isSelected -> BrandPrimary
                                        else -> OutlineVariantColor
                                    },
                                    shape = RoundedCornerShape(14.dp)
                                )
                                .clickable(enabled = !isMatched) {
                                    selectedEnglish = eng
                                    errorMessage = null

                                    // Check match if Target word is already chosen
                                    selectedTarget?.let { target ->
                                        val match = items.find { it.targetWord == target && it.englishWord == eng }
                                        if (match != null) {
                                            matchedPairs.add(target)
                                            selectedTarget = null
                                            selectedEnglish = null
                                        } else {
                                            errorMessage = "Try again!"
                                            selectedTarget = null
                                            selectedEnglish = null
                                        }
                                    }
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = eng,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontWeight = if (isSelected || isMatched) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isMatched) BrandSecondary else TextOnSurface
                                ),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }

        // Action Button
        Button(
            onClick = onNext,
            enabled = matchedPairs.size == items.size,
            modifier = Modifier.fillMaxWidth().height(52.dp).padding(top = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandSecondary)
        ) {
            Text("CONTINUE (+20 XP)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
    }
}

// --------------------------------------------------------------------------
// 3. INTERACTIVE AUDIO LISTENING COMPREHENSION
// --------------------------------------------------------------------------
@Composable
fun InteractiveListeningView(
    exercise: InteractiveExercise,
    onPlayAudio: (String) -> Unit,
    onNext: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = exercise.prompt,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            // Audio Controls (Normal & Turtle Slow Speed)
            Card(
                colors = CardDefaults.cardColors(containerColor = BrandPrimaryFixed.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Listen carefully to the audio:",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextOnSurfaceVariant)
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Normal Speed Speaker
                        FilledIconButton(
                            onClick = { onPlayAudio(exercise.targetText) },
                            modifier = Modifier.size(64.dp),
                            colors = IconButtonDefaults.filledIconButtonColors(containerColor = BrandPrimary)
                        ) {
                            Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Play normal", modifier = Modifier.size(32.dp))
                        }

                        // Turtle Slow Speed (Spaced pronunciation)
                        FilledTonalIconButton(
                            onClick = {
                                val slowed = exercise.targetText.toCharArray().joinToString(" ")
                                onPlayAudio(slowed)
                            },
                            modifier = Modifier.size(52.dp)
                        ) {
                            Text("🐢", fontSize = 24.sp)
                        }
                    }

                    Text(
                        text = "Tap 🐢 for slow speech",
                        style = MaterialTheme.typography.labelSmall.copy(color = TextOnSurfaceVariant)
                    )
                }
            }

            // Options List
            exercise.options.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                Card(
                    onClick = { if (!isChecked) selectedIndex = index },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            isChecked && index == exercise.correctOptionIndex -> BrandSecondaryContainer.copy(alpha = 0.4f)
                            isChecked && isSelected && index != exercise.correctOptionIndex -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                            isSelected -> BrandPrimaryFixed.copy(alpha = 0.5f)
                            else -> SurfaceContainerLowest
                        }
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            when {
                                isChecked && index == exercise.correctOptionIndex -> BrandSecondary
                                isChecked && isSelected && index != exercise.correctOptionIndex -> MaterialTheme.colorScheme.error
                                isSelected -> BrandPrimary
                                else -> OutlineVariantColor
                            }
                        ),
                        width = if (isSelected) 2.dp else 1.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { if (!isChecked) selectedIndex = index }
                        )
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }

        // Action Button
        Button(
            onClick = {
                if (!isChecked) {
                    isChecked = true
                } else {
                    onNext()
                }
            },
            enabled = selectedIndex != null,
            modifier = Modifier.fillMaxWidth().height(52.dp).padding(top = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isChecked) BrandSecondary else BrandPrimary
            )
        ) {
            Text(if (!isChecked) "CHECK ANSWER" else "CONTINUE", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
    }
}

// --------------------------------------------------------------------------
// 4. INTERACTIVE SPEAKING ECHO CHALLENGE
// --------------------------------------------------------------------------
@Composable
fun InteractiveSpeakingEchoView(
    exercise: InteractiveExercise,
    onPlayAudio: (String) -> Unit,
    onNext: () -> Unit
) {
    var isPracticed by remember { mutableStateOf(false) }
    var nativeMatchScore by remember { mutableStateOf(95) }
    var isListeningMic by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = exercise.prompt,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center
            )

            // Target Phrase Card
            Card(
                colors = CardDefaults.cardColors(containerColor = BrandPrimaryFixed.copy(alpha = 0.35f)),
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = exercise.targetText,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold),
                        textAlign = TextAlign.Center
                    )

                    if (exercise.pronunciation.isNotEmpty()) {
                        Text(
                            text = exercise.pronunciation,
                            style = MaterialTheme.typography.bodyMedium.copy(color = TextOnSurfaceVariant)
                        )
                    }

                    Text(
                        text = "\"${exercise.englishText}\"",
                        style = MaterialTheme.typography.bodyMedium.copy(color = TextOnSurfaceVariant)
                    )

                    OutlinedButton(
                        onClick = { onPlayAudio(exercise.targetText) },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Listen to Native Pronunciation")
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Big Interactive Microphone Button
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(if (isListeningMic) BrandSecondaryContainer else BrandPrimaryContainer)
                    .clickable {
                        isListeningMic = true
                        nativeMatchScore = (92..98).random()
                        isPracticed = true
                        isListeningMic = false
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Speak Phrase",
                    tint = Color.White,
                    modifier = Modifier.size(44.dp)
                )
            }

            Text(
                text = if (!isPracticed) "Tap mic to practice speaking" else "Speaking Practice Recorded! ⭐",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )

            if (isPracticed) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BrandSecondaryContainer.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Native Accent Match: $nativeMatchScore%",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = OnBrandSecondaryContainer
                            )
                        )
                        Text(
                            text = "Excellent tone & rhythm! +25 XP",
                            style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant)
                        )
                    }
                }
            }
        }

        Button(
            onClick = onNext,
            enabled = isPracticed,
            modifier = Modifier.fillMaxWidth().height(52.dp).padding(top = 16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandSecondary)
        ) {
            Text("CONTINUE", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
        }
    }
}

// --------------------------------------------------------------------------
// 5. INTERACTIVE MULTIPLE CHOICE / FILL-IN-THE-BLANK
// --------------------------------------------------------------------------
@Composable
fun InteractiveMultipleChoiceView(
    exercise: InteractiveExercise,
    onPlayAudio: (String) -> Unit,
    onNext: () -> Unit
) {
    var selectedIndex by remember { mutableStateOf<Int?>(null) }
    var isChecked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = exercise.prompt,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )

            if (exercise.blankSentence.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BrandPrimaryFixed.copy(alpha = 0.35f)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = exercise.blankSentence,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(20.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else if (exercise.targetText.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(SurfaceContainerLowest)
                        .border(1.5.dp, BrandPrimaryFixedDim, RoundedCornerShape(16.dp))
                        .clickable { onPlayAudio(exercise.targetText) }
                        .padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier.size(40.dp).clip(CircleShape).background(BrandPrimaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = null, tint = Color.White)
                    }
                    Text(
                        text = exercise.targetText,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            exercise.options.forEachIndexed { index, option ->
                val isSelected = selectedIndex == index
                Card(
                    onClick = { if (!isChecked) selectedIndex = index },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when {
                            isChecked && index == exercise.correctOptionIndex -> BrandSecondaryContainer.copy(alpha = 0.4f)
                            isChecked && isSelected && index != exercise.correctOptionIndex -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.4f)
                            isSelected -> BrandPrimaryFixed.copy(alpha = 0.5f)
                            else -> SurfaceContainerLowest
                        }
                    ),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = androidx.compose.ui.graphics.SolidColor(
                            when {
                                isChecked && index == exercise.correctOptionIndex -> BrandSecondary
                                isChecked && isSelected && index != exercise.correctOptionIndex -> MaterialTheme.colorScheme.error
                                isSelected -> BrandPrimary
                                else -> OutlineVariantColor
                            }
                        ),
                        width = if (isSelected) 2.dp else 1.dp
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        RadioButton(selected = isSelected, onClick = { if (!isChecked) selectedIndex = index })
                        Text(option, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium))
                    }
                }
            }
        }

        Column(modifier = Modifier.fillMaxWidth().padding(top = 16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            if (isChecked && exercise.explanation.isNotEmpty()) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = BrandSecondaryContainer.copy(alpha = 0.5f)),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        text = exercise.explanation,
                        style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }

            Button(
                onClick = {
                    if (!isChecked) {
                        isChecked = true
                    } else {
                        onNext()
                    }
                },
                enabled = selectedIndex != null,
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = if (isChecked) BrandSecondary else BrandPrimary)
            ) {
                Text(if (!isChecked) "CHECK" else "CONTINUE", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

// --------------------------------------------------------------------------
// 6. CELEBRATION COMPLETION VIEW
// --------------------------------------------------------------------------
@Composable
fun LessonCelebrationView(
    nodeTitle: String,
    languageName: String,
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(BrandSecondaryContainer.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = null,
                    tint = BrandSecondary,
                    modifier = Modifier.size(64.dp)
                )
            }

            Text(
                text = "Lesson Mastered! 🎉",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.ExtraBold),
                textAlign = TextAlign.Center
            )

            Text(
                text = "You've successfully completed \"$nodeTitle\" in $languageName!",
                style = MaterialTheme.typography.bodyLarge.copy(color = TextOnSurfaceVariant),
                textAlign = TextAlign.Center
            )
        }

        // Stats Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.width(140.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("⚡ XP EARNED", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandPrimary))
                    Text("+30 XP", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold))
                }
            }

            Card(
                colors = CardDefaults.cardColors(containerColor = SurfaceContainerLowest),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.width(140.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("🎯 ACCURACY", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandSecondary))
                    Text("100%", style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.ExtraBold))
                }
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().height(54.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BrandSecondary)
        ) {
            Text("CLAIM REWARDS & CONTINUE", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold))
        }
    }
}
