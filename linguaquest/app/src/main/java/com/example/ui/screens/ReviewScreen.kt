package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.PairMatchItem
import com.example.model.ReviewCard
import com.example.ui.theme.*

enum class ReviewMode {
    FLIP_CARDS,
    SPEED_MATCH,
    VOCAB_LIST
}

@Composable
fun ReviewScreen(
    customCards: List<ReviewCard> = emptyList(),
    languageName: String = "French",
    onPlayAudio: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val reviewCards = remember(customCards) {
        if (customCards.isNotEmpty()) {
            customCards
        } else {
            listOf(
                ReviewCard("Bonjour", "Hello / Good morning", "[bɔ̃.ʒuʁ]", "Bonjour, comment allez-vous ?", "Hello, how are you?", "Essential Greetings"),
                ReviewCard("S'il vous plaît", "Please (formal)", "[sil vu plɛ]", "Un café, s'il vous plaît.", "A coffee, please.", "Polite Expressions"),
                ReviewCard("Je voudrais", "I would like", "[ʒə vu.dʁɛ]", "Je voudrais une table pour deux.", "I would like a table for two.", "Dining Out"),
                ReviewCard("Merci beaucoup", "Thank you very much", "[mɛʁ.si bo.ku]", "Merci beaucoup pour l'aide !", "Thank you very much for the help!", "Gratitude"),
                ReviewCard("L'addition", "The bill / check", "[la.di.sjɔ̃]", "L'addition, s'il vous plaît.", "The check, please.", "Dining Out"),
                ReviewCard("Enchanté", "Nice to meet you", "[ɑ̃.ʃɑ̃.te]", "Enchanté de faire votre connaissance.", "Nice to meet you.", "Introductions")
            )
        }
    }

    var currentMode by remember { mutableStateOf(ReviewMode.FLIP_CARDS) }
    var selectedCategory by remember { mutableStateOf("All") }

    val categories = remember(reviewCards) {
        listOf("All") + reviewCards.map { it.category }.distinct()
    }

    val filteredCards = remember(selectedCategory, reviewCards) {
        if (selectedCategory == "All") reviewCards
        else reviewCards.filter { it.category == selectedCategory }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Review Header
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "Review & Practice",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextOnSurface
                )
            )
            Text(
                text = "Interactive spaced repetition & speed challenges in $languageName.",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = TextOnSurfaceVariant
                )
            )
        }

        // Mode Navigation Tabs
        SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
            SegmentedButton(
                selected = currentMode == ReviewMode.FLIP_CARDS,
                onClick = { currentMode = ReviewMode.FLIP_CARDS },
                shape = SegmentedButtonDefaults.itemShape(index = 0, count = 3)
            ) {
                Text("🎴 Flashcards")
            }
            SegmentedButton(
                selected = currentMode == ReviewMode.SPEED_MATCH,
                onClick = { currentMode = ReviewMode.SPEED_MATCH },
                shape = SegmentedButtonDefaults.itemShape(index = 1, count = 3)
            ) {
                Text("⚡ Speed Match")
            }
            SegmentedButton(
                selected = currentMode == ReviewMode.VOCAB_LIST,
                onClick = { currentMode = ReviewMode.VOCAB_LIST },
                shape = SegmentedButtonDefaults.itemShape(index = 2, count = 3)
            ) {
                Text("📚 Library")
            }
        }

        // Category Filter Chips
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            items(categories) { category ->
                val isSelected = category == selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { selectedCategory = category },
                    label = { Text(category) },
                    leadingIcon = if (isSelected) {
                        { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp)) }
                    } else null
                )
            }
        }

        // Mode Content
        when (currentMode) {
            ReviewMode.FLIP_CARDS -> {
                InteractiveFlipCardDeck(
                    cards = filteredCards,
                    onPlayAudio = onPlayAudio
                )
            }
            ReviewMode.SPEED_MATCH -> {
                InteractiveSpeedMatchChallenge(
                    cards = filteredCards,
                    onPlayAudio = onPlayAudio
                )
            }
            ReviewMode.VOCAB_LIST -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredCards) { card ->
                        ReviewFlashcardItem(card = card, onPlayAudio = onPlayAudio)
                    }
                }
            }
        }
    }
}

// --------------------------------------------------------------------------
// 1. INTERACTIVE 3D FLIP FLASHCARD DECK
// --------------------------------------------------------------------------
@Composable
fun InteractiveFlipCardDeck(
    cards: List<ReviewCard>,
    onPlayAudio: (String) -> Unit
) {
    if (cards.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No flashcards found in this category.", color = TextOnSurfaceVariant)
        }
        return
    }

    var cardIndex by remember { mutableStateOf(0) }
    var isFlipped by remember { mutableStateOf(false) }

    val safeIndex = cardIndex.coerceIn(0, cards.size - 1)
    val currentCard = cards[safeIndex]

    // 3D rotation animation
    val rotation by animateFloatAsState(
        targetValue = if (isFlipped) 180f else 0f,
        animationSpec = tween(durationMillis = 400, easing = FastOutSlowInEasing),
        label = "CardFlipAnimation"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Deck indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CARD ${safeIndex + 1} OF ${cards.size}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = BrandPrimary
                )
            )
            AssistChip(
                onClick = { isFlipped = !isFlipped },
                label = { Text("Tap card to flip 🔄") }
            )
        }

        // The 3D Animated Flip Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
                .graphicsLayer {
                    rotationY = rotation
                    cameraDistance = 12f * density
                }
                .clip(RoundedCornerShape(24.dp))
                .background(SurfaceContainerLowest)
                .border(2.dp, BrandPrimaryFixedDim, RoundedCornerShape(24.dp))
                .clickable { isFlipped = !isFlipped }
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            if (rotation <= 90f) {
                // Front Side: Target Foreign Word
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    AssistChip(
                        onClick = {},
                        label = { Text(currentCard.category, style = MaterialTheme.typography.labelSmall) }
                    )

                    Text(
                        text = currentCard.french,
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = BrandPrimary
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = currentCard.pronunciation,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = TextOnSurfaceVariant
                        )
                    )

                    FilledIconButton(
                        onClick = { onPlayAudio(currentCard.french) },
                        modifier = Modifier.size(52.dp),
                        colors = IconButtonDefaults.filledIconButtonColors(containerColor = BrandPrimaryContainer)
                    ) {
                        Icon(imageVector = Icons.Default.VolumeUp, contentDescription = "Listen", tint = Color.White)
                    }

                    Text(
                        text = "Tap to reveal English meaning & sentence",
                        style = MaterialTheme.typography.labelSmall.copy(color = TextOnSurfaceVariant)
                    )
                }
            } else {
                // Back Side (Rotated 180 degrees back so text reads normally)
                Column(
                    modifier = Modifier.graphicsLayer { rotationY = 180f },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "TRANSLATION",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandSecondary
                        )
                    )

                    Text(
                        text = currentCard.english,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        ),
                        textAlign = TextAlign.Center
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp), color = SurfaceContainerHighest)

                    Text(
                        text = "Example in Context:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandPrimary
                        )
                    )

                    Text(
                        text = "\"${currentCard.exampleFrench}\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = currentCard.exampleEnglish,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant
                        ),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // Bottom Deck Navigation Controls
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledTonalButton(
                onClick = {
                    if (cardIndex > 0) {
                        cardIndex--
                        isFlipped = false
                    }
                },
                enabled = safeIndex > 0,
                shape = RoundedCornerShape(14.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Previous")
                Spacer(modifier = Modifier.width(4.dp))
                Text("Previous")
            }

            Button(
                onClick = {
                    if (cardIndex < cards.size - 1) {
                        cardIndex++
                        isFlipped = false
                    } else {
                        cardIndex = 0
                        isFlipped = false
                    }
                },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandPrimary)
            ) {
                Text(if (safeIndex < cards.size - 1) "Next Card" else "Start Over 🔄")
                Spacer(modifier = Modifier.width(4.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = "Next")
            }
        }
    }
}

// --------------------------------------------------------------------------
// 2. INTERACTIVE SPEED MATCH CHALLENGE MINI-GAME
// --------------------------------------------------------------------------
@Composable
fun InteractiveSpeedMatchChallenge(
    cards: List<ReviewCard>,
    onPlayAudio: (String) -> Unit
) {
    val sampleCards = remember(cards) { cards.take(4) }
    val pairItems = remember(sampleCards) {
        sampleCards.mapIndexed { idx, c -> PairMatchItem(idx.toString(), c.french, c.english) }
    }

    var matchedCount by remember { mutableStateOf(0) }
    var selectedTarget by remember { mutableStateOf<String?>(null) }
    var selectedEnglish by remember { mutableStateOf<String?>(null) }
    val matchedSet = remember { mutableStateListOf<String>() }

    val targetWords = remember(pairItems) { pairItems.map { it.targetWord } }
    val englishWords = remember(pairItems) { pairItems.map { it.englishWord }.shuffled() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                colors = CardDefaults.cardColors(containerColor = BrandSecondaryContainer.copy(alpha = 0.4f)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("⚡ SPEED MATCH CHALLENGE", style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold, color = BrandSecondary))
                        Text("Match phrases as fast as you can!", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold))
                    }
                    Text("${matchedSet.size}/${pairItems.size} Done", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold, color = BrandSecondary))
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Left Column
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    targetWords.forEach { word ->
                        val isMatched = matchedSet.contains(word)
                        val isSelected = selectedTarget == word

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isMatched) BrandSecondaryContainer.copy(alpha = 0.4f) else if (isSelected) BrandPrimaryFixed.copy(alpha = 0.6f) else SurfaceContainerLowest)
                                .border(if (isSelected || isMatched) 2.dp else 1.dp, if (isMatched) BrandSecondary else if (isSelected) BrandPrimary else OutlineVariantColor, RoundedCornerShape(14.dp))
                                .clickable(enabled = !isMatched) {
                                    selectedTarget = word
                                    onPlayAudio(word)
                                    selectedEnglish?.let { eng ->
                                        val match = pairItems.find { it.targetWord == word && it.englishWord == eng }
                                        if (match != null) {
                                            matchedSet.add(word)
                                            selectedTarget = null
                                            selectedEnglish = null
                                        } else {
                                            selectedTarget = null
                                            selectedEnglish = null
                                        }
                                    }
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(word, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = if (isMatched) BrandSecondary else TextOnSurface), textAlign = TextAlign.Center)
                        }
                    }
                }

                // Right Column
                Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    englishWords.forEach { eng ->
                        val matching = pairItems.find { it.englishWord == eng }
                        val isMatched = matching != null && matchedSet.contains(matching.targetWord)
                        val isSelected = selectedEnglish == eng

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(58.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(if (isMatched) BrandSecondaryContainer.copy(alpha = 0.4f) else if (isSelected) BrandPrimaryFixed.copy(alpha = 0.6f) else SurfaceContainerLowest)
                                .border(if (isSelected || isMatched) 2.dp else 1.dp, if (isMatched) BrandSecondary else if (isSelected) BrandPrimary else OutlineVariantColor, RoundedCornerShape(14.dp))
                                .clickable(enabled = !isMatched) {
                                    selectedEnglish = eng
                                    selectedTarget?.let { target ->
                                        val match = pairItems.find { it.targetWord == target && it.englishWord == eng }
                                        if (match != null) {
                                            matchedSet.add(target)
                                            selectedTarget = null
                                            selectedEnglish = null
                                        } else {
                                            selectedTarget = null
                                            selectedEnglish = null
                                        }
                                    }
                                }
                                .padding(8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(eng, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, color = if (isMatched) BrandSecondary else TextOnSurface), textAlign = TextAlign.Center)
                        }
                    }
                }
            }
        }

        if (matchedSet.size == pairItems.size) {
            Button(
                onClick = {
                    matchedSet.clear()
                    selectedTarget = null
                    selectedEnglish = null
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BrandSecondary)
            ) {
                Text("PLAY AGAIN (+15 XP)", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
            }
        }
    }
}

// --------------------------------------------------------------------------
// 3. FLASHCARD LIST ITEM
// --------------------------------------------------------------------------
@Composable
fun ReviewFlashcardItem(
    card: ReviewCard,
    onPlayAudio: (String) -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLowest)
            .border(1.5.dp, OutlineVariantColor, RoundedCornerShape(16.dp))
            .clickable { isExpanded = !isExpanded }
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = card.french,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandPrimary
                            )
                        )
                        Text(
                            text = card.pronunciation,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextOnSurfaceVariant,
                                fontSize = 12.sp
                            )
                        )
                    }
                    Text(
                        text = card.english,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }

                IconButton(
                    onClick = { onPlayAudio(card.french) },
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(SurfaceContainerLow)
                ) {
                    Icon(
                        imageVector = Icons.Default.VolumeUp,
                        contentDescription = "Pronounce",
                        tint = BrandPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            if (isExpanded) {
                HorizontalDivider(color = SurfaceContainerHighest)
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Example:",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandSecondary
                        )
                    )
                    Text(
                        text = "\"${card.exampleFrench}\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = TextOnSurface
                        )
                    )
                    Text(
                        text = card.exampleEnglish,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant
                        )
                    )
                }
            }
        }
    }
}
