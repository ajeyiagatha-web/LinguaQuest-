package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LeaderboardUser
import com.example.ui.theme.*

@Composable
fun LeaderboardScreen(
    userCurrentXp: Int,
    modifier: Modifier = Modifier
) {
    val users = remember(userCurrentXp) {
        listOf(
            LeaderboardUser(1, "Sophia M.", "", 640, "🇫🇷"),
            LeaderboardUser(2, "Lucas B.", "", 590, "🇨🇦"),
            LeaderboardUser(3, "You", "", 420 + userCurrentXp, "🇫🇷", isCurrentUser = true),
            LeaderboardUser(4, "Emma Watson", "", 395, "🇬🇧"),
            LeaderboardUser(5, "Antoine Dupont", "", 340, "🇫🇷"),
            LeaderboardUser(6, "Elena Rostova", "", 290, "🇪🇸"),
            LeaderboardUser(7, "Mateo Silva", "", 245, "🇧🇷")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Emerald League Header Banner
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(BrandSecondaryContainer)
                .border(2.dp, BrandSecondary, RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(BrandSecondary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.EmojiEvents,
                            contentDescription = "League Trophy",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column {
                        Text(
                            text = "Emerald League",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = OnBrandSecondaryContainer
                            )
                        )
                        Text(
                            text = "Top 10 advance to Ruby League!",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnBrandSecondaryContainer.copy(alpha = 0.8f)
                            )
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Time Left",
                        tint = OnBrandSecondaryContainer,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "2d left",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnBrandSecondaryContainer
                        )
                    )
                }
            }
        }

        // Leaderboard Ranking List
        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(users) { user ->
                val isMe = user.isCurrentUser
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(if (isMe) BrandPrimaryFixed else SurfaceContainerLowest)
                        .border(
                            width = if (isMe) 2.dp else 1.dp,
                            color = if (isMe) BrandPrimary else OutlineVariantColor,
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Text(
                                text = "#${user.rank}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.ExtraBold,
                                    color = if (user.rank <= 3) GemGold else TextOnSurfaceVariant
                                ),
                                modifier = Modifier.width(32.dp)
                            )

                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(if (isMe) BrandPrimaryContainer else SurfaceContainerHigh),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user.name.take(1),
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = if (isMe) Color.White else TextOnSurface
                                    )
                                )
                            }

                            Column {
                                Text(
                                    text = "${user.name} ${user.flag}",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isMe) FontWeight.ExtraBold else FontWeight.Bold,
                                        color = if (isMe) OnBrandPrimaryFixed else TextOnSurface
                                    )
                                )
                                if (isMe) {
                                    Text(
                                        text = "Promotion Zone",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = BrandSecondary,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 10.sp
                                        )
                                    )
                                }
                            }
                        }

                        Text(
                            text = "${user.xp} XP",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = BrandPrimary
                            )
                        )
                    }
                }
            }
        }
    }
}
