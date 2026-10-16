package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import com.example.model.LearningUnit
import com.example.model.NodeState
import com.example.model.NodeType
import com.example.model.PathNode
import com.example.ui.components.CAMILLE_AVATAR_URL_1
import com.example.ui.components.CamilleAvatar
import com.example.ui.theme.*
import com.example.viewmodel.AppTab
import com.example.viewmodel.LinguaUiState

@Composable
fun LearnScreen(
    state: LinguaUiState,
    onNodeClick: (PathNode) -> Unit,
    onGuidebookClick: () -> Unit,
    onChatNowClick: () -> Unit,
    onStreakClick: () -> Unit = {},
    onSelectUnit: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // 0.5. Interactive Multi-Topic Unit Carousel
        if (state.units.isNotEmpty()) {
            UnitTopicSelectorCarousel(
                units = state.units,
                selectedIndex = state.selectedUnitIndex,
                onSelectUnit = onSelectUnit
            )
        }

        // 1. Unit Header Banner Card
        UnitHeaderBannerCard(
            unitNumber = state.selectedUnitIndex + 1,
            unitTitle = state.unitTitle,
            unitSubtitle = state.unitSubtitle,
            progressPercent = state.unitProgressPercent,
            onGuidebookClick = onGuidebookClick
        )

        // 1.2. Free Lesson Session Counter & Access Status
        SessionCounterCard(
            freeLessonsCompleted = state.freeLessonsCompleted,
            maxFreeLessons = state.maxFreeLessons,
            hasLifetimeAccess = state.hasLifetimeAccess
        )

        // 1.5. Daily Streak Counter Banner (Room DB Persisted)
        DailyStreakBannerCard(
            currentStreak = state.streaks,
            longestStreak = state.longestStreak,
            freezes = state.streakFreezeCount,
            onClick = onStreakClick
        )

        // 2. Daily Goal & Quest Widget
        DailyQuestCard(
            currentXp = state.dailyXpCurrent,
            goalXp = state.dailyXpGoal
        )

        // 3. Interactive Winding Visual Learning Path
        WindingLearningPath(
            nodes = state.pathNodes,
            onNodeClick = onNodeClick
        )

        // 4. Floating AI Tutor Shortcut Badge
        AiTutorShortcutBanner(
            tutorName = state.tutorName,
            tutorCatchphrase = state.tutorCatchphrase,
            onChatNowClick = onChatNowClick
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

/**
 * Daily Streak Counter Banner (Room DB Persisted)
 */
@Composable
fun DailyStreakBannerCard(
    currentStreak: Int,
    longestStreak: Int,
    freezes: Int,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(StreakBg)
            .border(width = 1.5.dp, color = StreakBorder, RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .border(1.5.dp, StreakBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalFireDepartment,
                        contentDescription = "Streak Fire",
                        tint = StreakRed,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "$currentStreak Day Streak",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = StreakRed
                            )
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(Color.White)
                                .border(1.dp, StreakBorder.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 6.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "ROOM DB",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp,
                                    color = StreakRed
                                )
                            )
                        }
                    }

                    Text(
                        text = "Best: $longestStreak days • $freezes Freezes equipped",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = "Logs",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = StreakRed
                    )
                )
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View Details",
                    tint = StreakRed,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

/**
 * Interactive Unit & Topic Switcher Carousel
 */
@Composable
fun UnitTopicSelectorCarousel(
    units: List<LearningUnit>,
    selectedIndex: Int,
    onSelectUnit: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "COURSE TOPICS & MODULES",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = BrandPrimary,
                    letterSpacing = 1.sp
                )
            )
            Text(
                text = "Unit ${selectedIndex + 1} of ${units.size}",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextOnSurfaceVariant
                )
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(horizontal = 2.dp)
        ) {
            itemsIndexed(units) { index, unit ->
                val isSelected = index == selectedIndex
                val themeColor = Color(unit.themeColorHex)

                Box(
                    modifier = Modifier
                        .width(170.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) themeColor.copy(alpha = 0.15f) else SurfaceContainerLowest
                        )
                        .border(
                            width = if (isSelected) 2.5.dp else 1.dp,
                            color = if (isSelected) themeColor else OutlineVariantColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clickable { onSelectUnit(index) }
                        .padding(12.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(unit.emoji, fontSize = 24.sp)
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (isSelected) themeColor else SurfaceContainerHighest)
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "U${unit.unitNumber}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (isSelected) Color.White else TextOnSurfaceVariant,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 10.sp
                                    )
                                )
                            }
                        }

                        Text(
                            text = unit.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextOnSurface
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        Text(
                            text = unit.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = TextOnSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        LinearProgressIndicator(
                            progress = { unit.progressPercent / 100f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = themeColor,
                            trackColor = SurfaceContainerHighest
                        )
                    }
                }
            }
        }
    }
}

/**
 * Unit Banner Card
 */
@Composable
fun UnitHeaderBannerCard(
    unitTitle: String = "Bonjour & Basics",
    unitSubtitle: String = "Master essential greetings and introductions",
    progressPercent: Int,
    onGuidebookClick: () -> Unit,
    unitNumber: Int = 1
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceContainerLowest)
            .border(width = 2.dp, color = OutlineVariantColor, RoundedCornerShape(20.dp))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.padding(bottom = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(BrandPrimaryContainer)
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "UNIT $unitNumber",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = Color.White,
                                    fontSize = 10.sp
                                )
                            )
                        }
                        Text(
                            text = "SECTION 1 DE 5",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextOnSurfaceVariant
                            )
                        )
                    }

                    Text(
                        text = unitTitle,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = TextOnSurface
                        )
                    )

                    Text(
                        text = unitSubtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant
                        )
                    )
                }

                // Guidebook Tactile Button
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(SurfaceContainerLowest)
                        .border(width = 1.5.dp, color = OutlineVariantColor, RoundedCornerShape(12.dp))
                        .clickable(onClick = onGuidebookClick)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = "Guidebook",
                            tint = BrandPrimary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Guidebook",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = BrandPrimary
                            )
                        )
                    }
                }
            }

            // Unit Progress Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(14.dp)
                        .clip(RoundedCornerShape(9999.dp))
                        .background(SurfaceContainerHighest)
                        .border(1.dp, OutlineVariantColor, RoundedCornerShape(9999.dp))
                        .padding(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(fraction = (progressPercent / 100f).coerceIn(0f, 1f))
                            .clip(RoundedCornerShape(9999.dp))
                            .background(BrandSecondaryContainer)
                    )
                }

                Text(
                    text = "$progressPercent%",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = OnBrandSecondaryContainer
                    )
                )
            }
        }
    }
}

/**
 * Daily Goal & Quest Widget matching Image 1
 */
@Composable
fun DailyQuestCard(
    currentXp: Int,
    goalXp: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLowest)
            .border(width = 2.dp, color = OutlineVariantColor, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Gold flag badge with bottom extrusion
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(BrandTertiaryFixedDim, RoundedCornerShape(12.dp))
                        .padding(bottom = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(BrandTertiaryFixed, RoundedCornerShape(12.dp))
                            .border(width = 1.5.dp, color = BrandTertiaryFixedDim, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Flag,
                            contentDescription = "Daily Quest",
                            tint = OnBrandTertiaryFixed,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }

                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Daily Quest: Earn $goalXp XP",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = TextOnSurface
                            )
                        )
                        Text(
                            text = "$currentXp/$goalXp",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = BrandSecondary
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    // Quest progress bar
                    Box(
                        modifier = Modifier
                            .width(140.dp)
                            .height(8.dp)
                            .clip(RoundedCornerShape(9999.dp))
                            .background(SurfaceContainerHighest)
                    ) {
                        val fraction = (currentXp.toFloat() / goalXp.toFloat()).coerceIn(0f, 1f)
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(fraction = fraction)
                                .clip(RoundedCornerShape(9999.dp))
                                .background(BrandTertiaryFixedDim)
                        )
                    }
                }
            }

            // Claimable Reward Preview Box
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerLow)
                    .border(width = 1.dp, color = OutlineVariantColor, RoundedCornerShape(12.dp))
                    .padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Inventory2,
                    contentDescription = "Reward",
                    tint = BrandTertiaryContainer,
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    text = "+15 XP",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = BrandTertiaryContainer
                    )
                )
            }
        }
    }
}

/**
 * Winding Visual Learning Path with Stepping Stone Nodes
 */
@Composable
fun WindingLearningPath(
    nodes: List<PathNode>,
    onNodeClick: (PathNode) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.TopCenter
    ) {
        // Curved SVG/Canvas connector path
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(490.dp)
        ) {
            val width = size.width
            val centerX = width / 2f

            // Coordinates matching the stepping stone vertical layout:
            // Node 1: y ~ 50, x = centerX
            // Node 2: y ~ 150, x = centerX + 48.dp
            // Node 3: y ~ 260, x = centerX
            // Node 4: y ~ 370, x = centerX - 48.dp
            // Node 5: y ~ 460, x = centerX
            val p1Y = 48.dp.toPx()
            val p2X = centerX + 48.dp.toPx()
            val p2Y = 150.dp.toPx()
            val p3X = centerX
            val p3Y = 256.dp.toPx()
            val p4X = centerX - 48.dp.toPx()
            val p4Y = 360.dp.toPx()
            val p5X = centerX
            val p5Y = 450.dp.toPx()

            val fullPath = Path().apply {
                moveTo(centerX, p1Y)
                cubicTo(centerX + 30.dp.toPx(), (p1Y + p2Y) / 2f, p2X, (p1Y + p2Y) / 2f, p2X, p2Y)
                cubicTo(p2X, (p2Y + p3Y) / 2f, p3X, (p2Y + p3Y) / 2f, p3X, p3Y)
                cubicTo(p3X, (p3Y + p4Y) / 2f, p4X, (p3Y + p4Y) / 2f, p4X, p4Y)
                cubicTo(p4X, (p4Y + p5Y) / 2f, p5X, (p4Y + p5Y) / 2f, p5X, p5Y)
            }

            // Background path track
            drawPath(
                path = fullPath,
                color = Color(0xFFDEE1FE),
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )

            // Completed segment track
            val completedPath = Path().apply {
                moveTo(centerX, p1Y)
                cubicTo(centerX + 30.dp.toPx(), (p1Y + p2Y) / 2f, p2X, (p1Y + p2Y) / 2f, p2X, p2Y)
                cubicTo(p2X, (p2Y + p3Y) / 2f, p3X, (p2Y + p3Y) / 2f, p3X, p3Y)
            }
            drawPath(
                path = completedPath,
                color = Color(0xFF6FF7C5),
                style = Stroke(width = 12.dp.toPx(), cap = StrokeCap.Round)
            )
        }

        // Stepping Stones vertical column
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(28.dp)
        ) {
            nodes.forEach { node ->
                SteppingStoneNodeItem(
                    node = node,
                    onClick = { onNodeClick(node) }
                )
            }
        }
    }
}

@Composable
fun SteppingStoneNodeItem(
    node: PathNode,
    onClick: () -> Unit
) {
    val offsetXDp = node.xOffsetDp.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .offset(x = offsetXDp)
            .clickable(
                enabled = node.state != NodeState.LOCKED,
                onClick = onClick
            )
    ) {
        when (node.state) {
            NodeState.COMPLETED -> {
                Box(contentAlignment = Alignment.TopEnd) {
                    // Tactile circular button
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(BrandSecondaryBase, CircleShape)
                            .padding(bottom = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(BrandSecondary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (node.type == NodeType.PHRASES) Icons.Filled.ChatBubble else Icons.Filled.Check,
                                contentDescription = "${node.title} Completed",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }

                    // Gold Crown badge
                    if (node.hasCrown) {
                        Box(
                            modifier = Modifier
                                .offset(x = 6.dp, y = (-8).dp)
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(GemBg)
                                .border(2.dp, GemBorder, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.EmojiEvents,
                                contentDescription = "Mastered Crown",
                                tint = GemGold,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            NodeState.ACTIVE -> {
                // Pulsating concentric halo animation
                val infiniteTransition = rememberInfiniteTransition(label = "pulse_halo")
                val haloScale by infiniteTransition.animateFloat(
                    initialValue = 0.95f,
                    targetValue = 1.25f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1800, easing = FastOutSlowInEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "halo_scale"
                )

                // Floating START Callout Tooltip
                val floatOffset by infiniteTransition.animateFloat(
                    initialValue = 0f,
                    targetValue = -5f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1200, easing = EaseInOutQuad),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "float_offset"
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    // START callout tooltip
                    Box(
                        modifier = Modifier
                            .offset(y = floatOffset.dp)
                            .padding(bottom = 6.dp)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(9999.dp))
                                    .background(BrandPrimary)
                                    .border(1.dp, BrandPrimaryFixed, RoundedCornerShape(9999.dp))
                                    .padding(horizontal = 12.dp, vertical = 4.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.PlayArrow,
                                        contentDescription = "Start Lesson",
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = "START",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.ExtraBold,
                                            color = Color.White
                                        )
                                    )
                                }
                            }
                        }
                    }

                    // Tactile active circle with pulsing ring
                    Box(contentAlignment = Alignment.Center) {
                        // Halo ring
                        Box(
                            modifier = Modifier
                                .size(90.dp)
                                .scale(haloScale)
                                .clip(CircleShape)
                                .background(BrandPrimaryContainer.copy(alpha = 0.22f))
                        )

                        // Button
                        Box(
                            modifier = Modifier
                                .size(76.dp)
                                .background(BrandPrimary, CircleShape)
                                .padding(bottom = 6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(BrandPrimaryContainer, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Star,
                                    contentDescription = "Everyday Words - Current Lesson",
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                }
            }

            NodeState.UNLOCKED -> {
                // Unlocked White Button with Purple Border
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(OutlineVariantColor, CircleShape)
                        .padding(bottom = 6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(SurfaceContainerLowest, CircleShape)
                            .border(2.dp, OutlineVariantColor, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.HotelClass,
                            contentDescription = "${node.title} Unlocked",
                            tint = BrandPrimary,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }

            NodeState.LOCKED -> {
                // Locked Gray Chest
                Box(contentAlignment = Alignment.TopEnd) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .background(LockedBorder, CircleShape)
                            .padding(bottom = 6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(LockedGray, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Redeem,
                                contentDescription = "${node.title} Locked",
                                tint = OutlineColor,
                                modifier = Modifier.size(34.dp)
                            )
                        }
                    }

                    // Padlock overlay
                    Box(
                        modifier = Modifier
                            .offset(x = 4.dp, y = (-4).dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(TextOnSurface)
                            .border(1.5.dp, SurfaceContainerLowest, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(13.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        // Node Title Chip
        val isCurrent = node.state == NodeState.ACTIVE
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(9999.dp))
                .background(if (isCurrent) BrandPrimaryFixed else SurfaceContainerLowest)
                .border(
                    width = 1.dp,
                    color = if (isCurrent) BrandPrimary else OutlineVariantColor,
                    shape = RoundedCornerShape(9999.dp)
                )
                .padding(horizontal = 10.dp, vertical = 3.dp)
        ) {
            Text(
                text = node.title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = if (isCurrent) BrandPrimary else TextOnSurface
                )
            )
        }
    }
}

/**
 * Floating AI Tutor Shortcut Banner matching Image 1
 */
@Composable
fun AiTutorShortcutBanner(
    tutorName: String = "Camille",
    tutorCatchphrase: String = "Prêt pour pratiquer les salutations ?",
    onChatNowClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(SurfaceContainerLowest)
            .border(width = 1.5.dp, color = BrandPrimary.copy(alpha = 0.35f), RoundedCornerShape(20.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Camille avatar with online badge
                CamilleAvatar(
                    avatarUrl = CAMILLE_AVATAR_URL_1,
                    size = 48.dp,
                    showOnlineStatus = true
                )

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(4.dp))
                                .background(BrandPrimaryFixed)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "AI TUTOR",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = BrandPrimary,
                                    fontSize = 10.sp
                                )
                            )
                        }

                        Text(
                            text = tutorName,
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextOnSurface,
                                fontSize = 15.sp
                            )
                        )
                    }

                    Text(
                        text = tutorCatchphrase,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant,
                            fontSize = 12.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Chat Now Button
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(14.dp))
                    .background(BrandPrimaryBase)
                    .padding(bottom = 2.5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(14.dp))
                        .background(BrandPrimary)
                        .clickable(onClick = onChatNowClick)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Chat Now",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = Color.White
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.Forum,
                            contentDescription = "Chat",
                            tint = Color.White,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SessionCounterCard(
    freeLessonsCompleted: Int,
    maxFreeLessons: Int,
    hasLifetimeAccess: Boolean,
    modifier: Modifier = Modifier
) {
    if (hasLifetimeAccess) {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = SurfaceCard,
            border = CardBorder.copy(brush = Brush.horizontalGradient(listOf(GoldAccent, BrandPrimary))),
            modifier = modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(GoldAccent.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Filled.WorkspacePremium,
                        contentDescription = "Lifetime Unlocked",
                        tint = GoldAccent,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Unlimited Lifetime Access",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                    Text(
                        text = "All features & languages unlocked with zero ads or limits",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextSecondary
                        )
                    )
                }
            }
        }
    } else {
        Surface(
            shape = RoundedCornerShape(20.dp),
            color = SurfaceCard,
            border = CardBorder,
            modifier = modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(CircleShape)
                                .background(BrandPrimary.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.School,
                                contentDescription = null,
                                tint = BrandPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Free Lessons Session",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = TextPrimary
                                )
                            )
                            val remaining = (maxFreeLessons - freeLessonsCompleted).coerceAtLeast(0)
                            Text(
                                text = if (remaining > 0) "$remaining free practice lesson(s) remaining" else "3 free lessons used",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = if (remaining == 0) MaterialTheme.colorScheme.error else TextSecondary
                                )
                            )
                        }
                    }

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = if (freeLessonsCompleted >= maxFreeLessons) MaterialTheme.colorScheme.error.copy(alpha = 0.12f) else BrandPrimary.copy(alpha = 0.12f)
                    ) {
                        Text(
                            text = "$freeLessonsCompleted / $maxFreeLessons",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = if (freeLessonsCompleted >= maxFreeLessons) MaterialTheme.colorScheme.error else BrandPrimary
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }

                LinearProgressIndicator(
                    progress = { (freeLessonsCompleted.toFloat() / maxFreeLessons).coerceIn(0f, 1f) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .clip(CircleShape),
                    color = if (freeLessonsCompleted >= maxFreeLessons) MaterialTheme.colorScheme.error else BrandPrimary,
                    trackColor = CardBorderColor
                )
            }
        }
    }
}
