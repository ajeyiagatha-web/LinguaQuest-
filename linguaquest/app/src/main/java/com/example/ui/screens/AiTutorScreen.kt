package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.GrammarTip
import com.example.model.MessageSender
import com.example.ui.components.CAMILLE_AVATAR_URL_2
import com.example.ui.components.CamilleAvatar
import com.example.ui.theme.*
import com.example.viewmodel.LinguaUiState

@Composable
fun AiTutorScreen(
    state: LinguaUiState,
    onBackClick: () -> Unit,
    onChangeScenarioClick: () -> Unit,
    onPlayAudio: (String) -> Unit,
    onToggleTranslation: (String) -> Unit,
    onSuggestedReplyClick: (String) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onToggleKeyboard: () -> Unit,
    onTypedMessageChange: (String) -> Unit,
    onSendTypedMessage: () -> Unit,
    onHelpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    LaunchedEffect(state.chatMessages.size) {
        if (state.chatMessages.isNotEmpty()) {
            listState.animateScrollToItem(state.chatMessages.size)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .imePadding()
    ) {
        // 1. Top Header & Scenario Ribbon matching Image 3
        AiTutorHeader(
            scenarioTitle = state.currentScenario.title,
            scenarioEmoji = state.currentScenario.emoji,
            mission = state.currentScenario.mission,
            tutorName = state.tutorName,
            languageFlag = state.selectedLanguage.flag,
            onBackClick = onBackClick,
            onChangeScenarioClick = onChangeScenarioClick
        )

        // 2. Chat Timeline Messages
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 12.dp)
        ) {
            // Timestamp Divider: TODAY • 10:42 AM
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(OutlineVariantColor.copy(alpha = 0.6f))
                    )
                    Text(
                        text = "TODAY • 10:42 AM",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurfaceVariant
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(1.dp)
                            .background(OutlineVariantColor.copy(alpha = 0.6f))
                    )
                }
            }

            // Message items
            items(state.chatMessages, key = { it.id }) { msg ->
                if (msg.sender == MessageSender.AI) {
                    AiMessageBubble(
                        message = msg,
                        tutorName = state.tutorName,
                        onAudioClick = { onPlayAudio(msg.frenchText) },
                        onTranslateClick = { onToggleTranslation(msg.id) }
                    )
                } else {
                    UserMessageBubble(
                        message = msg,
                        onPlayAudio = { onPlayAudio(msg.frenchText) }
                    )
                }
            }

            // Real-time Grammar Tip Inset Card (if active)
            state.activeGrammarTip?.let { tip ->
                item {
                    SmartGrammarTipCard(tip = tip)
                }
            }
        }

        // 3. Interactive Bottom Control Hub & Quick Replies
        BottomControlHub(
            languageName = state.selectedLanguage.name,
            suggestedReplies = state.suggestedReplies,
            isRecording = state.isRecordingSpeech,
            isKeyboardMode = state.isKeyboardMode,
            typedMessage = state.typedMessage,
            onSuggestedReplyClick = onSuggestedReplyClick,
            onStartRecording = onStartRecording,
            onStopRecording = onStopRecording,
            onToggleKeyboard = onToggleKeyboard,
            onTypedMessageChange = onTypedMessageChange,
            onSendTypedMessage = onSendTypedMessage,
            onHelpClick = onHelpClick
        )
    }
}

/**
 * Top App Bar & Scenario Mission Header matching Image 3
 */
@Composable
fun AiTutorHeader(
    scenarioTitle: String,
    scenarioEmoji: String,
    mission: String,
    tutorName: String = "Camille",
    languageFlag: String = "🇫🇷",
    onBackClick: () -> Unit,
    onChangeScenarioClick: () -> Unit
) {
    Surface(
        color = SurfaceContainerLowest,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = OutlineVariantColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            // Row 1: Back + Avatar + Camille status + Level pill
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go Back",
                            tint = TextOnSurface,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Camille Avatar with online badge
                    CamilleAvatar(
                        avatarUrl = CAMILLE_AVATAR_URL_2,
                        size = 44.dp,
                        showOnlineStatus = true
                    )

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = tutorName,
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextOnSurface,
                                    fontSize = 17.sp
                                )
                            )
                            Text(text = languageFlag, fontSize = 13.sp)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(BrandPrimaryFixed)
                                    .padding(horizontal = 5.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "AI",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = BrandPrimary,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(BrandSecondary)
                            )
                            Text(
                                text = "Online • Gemini Flash",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = BrandSecondary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                // Level pill & Settings button
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                            .border(1.dp, OutlineVariantColor, RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.HotelClass,
                            contentDescription = "A1 Beginner",
                            tint = BrandTertiaryContainer,
                            modifier = Modifier.size(15.dp)
                        )
                        Text(
                            text = "A1 Beg",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextOnSurface
                            )
                        )
                    }

                    IconButton(
                        onClick = onChangeScenarioClick,
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLow)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Settings",
                            tint = TextOnSurfaceVariant,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Scenario Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Restaurant,
                        contentDescription = "Scenario",
                        tint = BrandPrimary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "Scenario: $scenarioTitle $scenarioEmoji",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        )
                    )
                }

                Row(
                    modifier = Modifier.clickable(onClick = onChangeScenarioClick),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Text(
                        text = "Change",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandPrimary
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = "Change Scenario",
                        tint = BrandPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Mission Ribbon
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerHigh.copy(alpha = 0.6f))
                    .border(1.dp, OutlineVariantColor, RoundedCornerShape(12.dp))
                    .padding(horizontal = 12.dp, vertical = 7.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.FlagCircle,
                    contentDescription = "Mission",
                    tint = BrandSecondary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Mission: $mission",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextOnSurface,
                        fontWeight = FontWeight.Normal
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

/**
 * AI Message Bubble with Audio, Translate, and Grammar Actions
 */
@Composable
fun AiMessageBubble(
    message: ChatMessage,
    tutorName: String = "Camille",
    onAudioClick: () -> Unit,
    onTranslateClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(0.92f),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Small AI Robot Avatar Pill
        Box(
            modifier = Modifier
                .padding(top = 4.dp)
                .size(32.dp)
                .clip(CircleShape)
                .background(BrandPrimaryFixed)
                .border(1.dp, BrandPrimary, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.SmartToy,
                contentDescription = "Camille",
                tint = BrandPrimary,
                modifier = Modifier.size(18.dp)
            )
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Tactile Bubble
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                    .background(SurfaceContainerLowest)
                    .border(2.dp, Color(0xFFDEE1FE), RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    // Natural Context tag if available
                    if (message.isNaturalContext) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "NATURAL CONTEXT",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BrandSecondary,
                                    fontSize = 10.sp
                                )
                            )
                            IconButton(
                                onClick = onAudioClick,
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(SurfaceContainerLow)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Play Audio",
                                    tint = BrandPrimary,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    }

                    // French Text
                    Text(
                        text = message.frenchText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium,
                            color = TextOnSurface,
                            lineHeight = 22.sp
                        )
                    )

                    // Bubble Toolbar: Audio, Translate, Grammar
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 0.5.dp, color = SurfaceContainer)
                            .padding(top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            // Audio Button
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(onClick = onAudioClick)
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.VolumeUp,
                                    contentDescription = "Listen Audio",
                                    tint = BrandPrimary,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Audio",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = BrandPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }

                            // Translate Button
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable(onClick = onTranslateClick)
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Translate,
                                    contentDescription = "Instant Translate",
                                    tint = TextOnSurfaceVariant,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = "Translate",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = TextOnSurfaceVariant,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }

                        // Vocab hint or Grammar badge
                        if (message.vocabHint != null) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(BrandTertiaryFixed.copy(alpha = 0.4f))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "💡 ${message.vocabHint}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BrandTertiaryContainer,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        } else {
                            Row(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(SurfaceContainerLow)
                                    .border(1.dp, OutlineVariantColor.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(3.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = "Grammar",
                                    tint = BrandPrimary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = "Grammar",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = BrandPrimary,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }

                    // English Translation Inset (toggleable)
                    if (message.isTranslationVisible && message.englishText.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(SurfaceContainerLow)
                                .border(1.dp, SurfaceContainerHighest, RoundedCornerShape(12.dp))
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "\"${message.englishText}\"",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontStyle = FontStyle.Italic,
                                    color = TextOnSurfaceVariant
                                )
                            )
                        }
                    }
                }
            }

            Text(
                text = "$tutorName • ${message.timestamp}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = TextOnSurfaceVariant,
                    fontSize = 11.sp
                ),
                modifier = Modifier.padding(start = 4.dp)
            )
        }
    }
}

/**
 * User Message Bubble with Voice Tag & Pronunciation Match Badge
 */
@Composable
fun UserMessageBubble(
    message: ChatMessage,
    onPlayAudio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 32.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        // Primary Blue/Purple Extruded Bubble
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                .background(BrandPrimary)
                .padding(bottom = 3.dp)
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomEnd = 16.dp, bottomStart = 16.dp))
                    .background(BrandPrimaryContainer)
                    .padding(14.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = message.frenchText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            lineHeight = 22.sp
                        )
                    )

                    if (message.isVoiceRecorded) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(width = 0.5.dp, color = Color.White.copy(alpha = 0.2f))
                                .padding(top = 6.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Voice recorded",
                                    tint = BrandPrimaryFixed,
                                    modifier = Modifier.size(14.dp)
                                )
                                Text(
                                    text = "Voice recorded",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = BrandPrimaryFixed,
                                        fontWeight = FontWeight.Medium
                                    )
                                )
                            }

                            IconButton(
                                onClick = onPlayAudio,
                                modifier = Modifier.size(22.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.PlayCircle,
                                    contentDescription = "Play recording",
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Gamified Accuracy Score Pill matching Image 3
        if (message.nativeMatchPercentage != null) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(BrandSecondaryContainer)
                    .border(1.dp, BrandSecondary, RoundedCornerShape(9999.dp))
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Verified,
                    contentDescription = "Verified Native Match",
                    tint = BrandSecondary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "${message.nativeMatchPercentage}% Native match!",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnBrandSecondaryContainer
                    )
                )
                Text(
                    text = "⭐ +${message.xpEarned ?: 15} XP",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrandSecondary
                    )
                )
            }
        }
    }
}

/**
 * Smart Grammar Tip Inset Card matching Image 3
 */
@Composable
fun SmartGrammarTipCard(
    tip: GrammarTip
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .border(2.dp, SurfaceContainerHighest, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Gold Lightbulb Icon Box
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(BrandTertiaryFixed),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = "Grammar Tip",
                    tint = OnBrandTertiaryFixed,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = tip.title,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        )
                    )
                    Text(
                        text = tip.tag,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandPrimary
                        )
                    )
                }

                Text(
                    text = tip.description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextOnSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}

/**
 * Interactive Bottom Control Hub & Quick Replies matching Image 3
 */
@Composable
fun BottomControlHub(
    languageName: String = "French",
    suggestedReplies: List<String>,
    isRecording: Boolean,
    isKeyboardMode: Boolean,
    typedMessage: String,
    onSuggestedReplyClick: (String) -> Unit,
    onStartRecording: () -> Unit,
    onStopRecording: () -> Unit,
    onToggleKeyboard: () -> Unit,
    onTypedMessageChange: (String) -> Unit,
    onSendTypedMessage: () -> Unit,
    onHelpClick: () -> Unit
) {
    Surface(
        color = SurfaceContainerLowest,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = OutlineVariantColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            // Suggested Replies Carousel
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.TipsAndUpdates,
                        contentDescription = "Suggested",
                        tint = BrandPrimary,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "SUGGESTED REPLIES",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextOnSurfaceVariant,
                            letterSpacing = 0.5.sp
                        )
                    )
                }

                Text(
                    text = "More ideas",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrandPrimary
                    ),
                    modifier = Modifier.clickable {
                        onSuggestedReplyClick("Une carafe d'eau s'il vous plaît 💧")
                    }
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Horizontal scrolling suggested chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                suggestedReplies.forEach { reply ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(SurfaceContainerLowest)
                            .border(width = 1.5.dp, color = Color(0xFFE8E8F3), RoundedCornerShape(12.dp))
                            .clickable { onSuggestedReplyClick(reply) }
                            .padding(horizontal = 12.dp, vertical = 7.dp)
                    ) {
                        Text(
                            text = reply,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = TextOnSurface
                            )
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Speech Input Dock
            if (isKeyboardMode) {
                // Keyboard Text Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    IconButton(
                        onClick = onToggleKeyboard,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(SurfaceContainerLow)
                            .border(1.5.dp, OutlineVariantColor, RoundedCornerShape(14.dp))
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mic,
                            contentDescription = "Voice mode",
                            tint = BrandPrimary
                        )
                    }

                    OutlinedTextField(
                        value = typedMessage,
                        onValueChange = onTypedMessageChange,
                        placeholder = { Text("Type $languageName response...", fontSize = 14.sp) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true
                    )

                    IconButton(
                        onClick = onSendTypedMessage,
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(BrandPrimary)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            } else {
                // Push-to-Talk Speech Trigger with Soundwave Animation
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Keyboard Mode Toggle Button
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowest)
                            .border(2.dp, OutlineVariantColor, RoundedCornerShape(16.dp))
                            .clickable(onClick = onToggleKeyboard),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Keyboard,
                            contentDescription = "Keyboard Input",
                            tint = TextOnSurface,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    // Main Push-to-Talk Speech Trigger
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(BrandPrimaryBase)
                            .padding(bottom = if (isRecording) 1.dp else 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (isRecording) BrandSecondary else BrandPrimaryContainer)
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onPress = {
                                            onStartRecording()
                                            tryAwaitRelease()
                                            onStopRecording()
                                        }
                                    )
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                // Soundwave Visualizer Bars
                                SoundwaveBars(isAnimating = isRecording)

                                Icon(
                                    imageVector = Icons.Default.Mic,
                                    contentDescription = "Speak",
                                    tint = Color.White,
                                    modifier = Modifier.size(24.dp)
                                )

                                Text(
                                    text = if (isRecording) "LISTENING..." else "HOLD TO SPEAK ${languageName.uppercase()}",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.ExtraBold,
                                        color = Color.White,
                                        letterSpacing = 0.5.sp
                                    )
                                )
                            }
                        }
                    }

                    // Hints / Help Button
                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(SurfaceContainerLowest)
                            .border(2.dp, OutlineVariantColor, RoundedCornerShape(16.dp))
                            .clickable(onClick = onHelpClick),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpCenter,
                            contentDescription = "Need Help",
                            tint = BrandTertiaryContainer,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }
}

/**
 * Animated Soundwave Bars for Speech Input
 */
@Composable
fun SoundwaveBars(isAnimating: Boolean) {
    val infiniteTransition = rememberInfiniteTransition(label = "soundwave")
    val height1 by infiniteTransition.animateFloat(
        initialValue = 6f,
        targetValue = 20f,
        animationSpec = infiniteRepeatable(tween(400, easing = EaseInOutQuad), RepeatMode.Reverse),
        label = "h1"
    )
    val height2 by infiniteTransition.animateFloat(
        initialValue = 18f,
        targetValue = 8f,
        animationSpec = infiniteRepeatable(tween(550, easing = EaseInOutQuad), RepeatMode.Reverse),
        label = "h2"
    )
    val height3 by infiniteTransition.animateFloat(
        initialValue = 10f,
        targetValue = 22f,
        animationSpec = infiniteRepeatable(tween(480, easing = EaseInOutQuad), RepeatMode.Reverse),
        label = "h3"
    )
    val height4 by infiniteTransition.animateFloat(
        initialValue = 14f,
        targetValue = 6f,
        animationSpec = infiniteRepeatable(tween(360, easing = EaseInOutQuad), RepeatMode.Reverse),
        label = "h4"
    )

    Row(
        horizontalArrangement = Arrangement.spacedBy(2.5.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.height(24.dp)
    ) {
        val bar1H = if (isAnimating) height1.dp else 8.dp
        val bar2H = if (isAnimating) height2.dp else 16.dp
        val bar3H = if (isAnimating) height3.dp else 12.dp
        val bar4H = if (isAnimating) height4.dp else 6.dp

        Box(modifier = Modifier.width(3.dp).height(bar1H).clip(CircleShape).background(Color.White.copy(alpha = 0.7f)))
        Box(modifier = Modifier.width(3.dp).height(bar2H).clip(CircleShape).background(Color.White.copy(alpha = 0.9f)))
        Box(modifier = Modifier.width(3.dp).height(bar3H).clip(CircleShape).background(Color.White))
        Box(modifier = Modifier.width(3.dp).height(bar4H).clip(CircleShape).background(Color.White.copy(alpha = 0.8f)))
    }
}
