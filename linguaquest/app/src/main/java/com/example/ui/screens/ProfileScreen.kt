package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ads.AdMobConfig
import com.example.ads.AdMobManager
import com.example.ui.theme.*
import com.example.viewmodel.LinguaUiState

@Composable
fun ProfileScreen(
    state: LinguaUiState,
    onLanguageClick: () -> Unit,
    onStreakClick: () -> Unit = {},
    onResetAccessClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(SurfaceBackground)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Header Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(SurfaceContainerLowest)
                .border(2.dp, OutlineVariantColor, RoundedCornerShape(20.dp))
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Large Avatar
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(BrandPrimaryContainer)
                        .border(3.dp, BrandPrimaryFixed, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "A",
                        style = MaterialTheme.typography.displayMedium.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Alex Mercer",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        )
                    )
                    Text(
                        text = "Joined October 2026 • Level 4 French Scholar",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TextOnSurfaceVariant
                        )
                    )
                }

                // Active Language Chip
                Button(
                    onClick = onLanguageClick,
                    shape = RoundedCornerShape(9999.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BrandPrimaryFixed)
                ) {
                    Text(
                        text = "Learning ${state.selectedLanguage.name} ${state.selectedLanguage.flag} (Change)",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnBrandPrimaryFixed
                        )
                    )
                }
            }
        }

        // Stats Grid
        Text(
            text = "Statistics",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextOnSurface
            )
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatGridItem(
                title = "Day Streak",
                value = "${state.streaks}",
                icon = Icons.Filled.LocalFireDepartment,
                iconTint = StreakRed,
                onClick = onStreakClick,
                modifier = Modifier.weight(1f)
            )
            StatGridItem(
                title = "Total Gems",
                value = "${state.gems}",
                icon = Icons.Filled.Diamond,
                iconTint = GemGold,
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatGridItem(
                title = "Unit Progress",
                value = "${state.unitProgressPercent}%",
                icon = Icons.Filled.CheckCircle,
                iconTint = BrandSecondary,
                modifier = Modifier.weight(1f)
            )
            StatGridItem(
                title = "Current League",
                value = "Emerald",
                icon = Icons.Filled.EmojiEvents,
                iconTint = BrandPrimary,
                modifier = Modifier.weight(1f)
            )
        }

        // Badges Section
        Text(
            text = "Achievements",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = TextOnSurface
            )
        )

        AchievementRow(
            title = "Parisian Conversationalist",
            description = "Chat with Camille in 3 realistic bistro scenarios",
            icon = Icons.Filled.Restaurant,
            isUnlocked = true
        )

        AchievementRow(
            title = "Pronunciation Pro",
            description = "Achieve 95%+ native voice match on 5 phrases",
            icon = Icons.Filled.Mic,
            isUnlocked = true
        )

        AchievementRow(
            title = "Weekend Champion",
            description = "Maintain a 7-day uninterrupted study streak",
            icon = Icons.Filled.LocalFireDepartment,
            isUnlocked = true
        )

        // Lifetime Membership & Free Lesson Session Status
        MembershipAccessCard(
            freeLessonsCompleted = state.freeLessonsCompleted,
            maxFreeLessons = state.maxFreeLessons,
            hasLifetimeAccess = state.hasLifetimeAccess,
            onResetClick = onResetAccessClick
        )

        // Google AdMob & Child Safety Settings & Diagnostics
        AdMobComplianceCard()
    }
}

@Composable
fun MembershipAccessCard(
    freeLessonsCompleted: Int,
    maxFreeLessons: Int,
    hasLifetimeAccess: Boolean,
    onResetClick: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceCard),
        border = CardBorder
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = if (hasLifetimeAccess) Icons.Filled.WorkspacePremium else Icons.Filled.VpnKey,
                        contentDescription = null,
                        tint = if (hasLifetimeAccess) GoldAccent else BrandPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Access & Membership",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextPrimary
                        )
                    )
                }

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = if (hasLifetimeAccess) GoldAccent.copy(alpha = 0.15f) else BrandPrimary.copy(alpha = 0.12f)
                ) {
                    Text(
                        text = if (hasLifetimeAccess) "LIFETIME 👑" else "FREE TIER",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.ExtraBold,
                            color = if (hasLifetimeAccess) GoldAccent else BrandPrimary
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }

            if (hasLifetimeAccess) {
                Text(
                    text = "You have unlocked Unlimited Lifetime Practice! All features, AI tutors, and languages are available with zero ads or limits.",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                )
            } else {
                Text(
                    text = "Free practice: $freeLessonsCompleted of $maxFreeLessons lessons completed. When the 3rd lesson is completed, a Lifetime Access Key is required.",
                    style = MaterialTheme.typography.bodySmall.copy(color = TextSecondary)
                )
            }

            if (onResetClick != null) {
                OutlinedButton(
                    onClick = onResetClick,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Reset Session Counter (Testing)", style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun StatGridItem(
    title: String,
    value: String,
    icon: ImageVector,
    iconTint: Color,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val clickableModifier = if (onClick != null) {
        modifier.clickable(onClick = onClick)
    } else {
        modifier
    }

    Box(
        modifier = clickableModifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLowest)
            .border(1.5.dp, OutlineVariantColor, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = TextOnSurface
                )
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextOnSurfaceVariant,
                    fontSize = 12.sp
                )
            )
        }
    }
}

@Composable
fun AchievementRow(
    title: String,
    description: String,
    icon: ImageVector,
    isUnlocked: Boolean
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLowest)
            .border(1.5.dp, OutlineVariantColor, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(if (isUnlocked) BrandPrimaryFixed else SurfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = if (isUnlocked) BrandPrimary else OutlineColor,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextOnSurface
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextOnSurfaceVariant,
                        fontSize = 12.sp
                    )
                )
            }
        }
    }
}

@Composable
fun AdMobComplianceCard() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "AdMob & Family Safety",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextOnSurface
                )
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(BrandPrimaryFixed)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = if (AdMobConfig.USE_TEST_ADS) "TEST ADS (SAFE)" else "PRODUCTION",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = BrandPrimary,
                        fontSize = 10.sp
                    )
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(SurfaceContainerLowest)
                .border(1.5.dp, OutlineVariantColor, RoundedCornerShape(16.dp))
                .padding(14.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.VerifiedUser,
                        contentDescription = "Child Safety Compliance",
                        tint = BrandPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Google Play Families Policy Compliant",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        )
                    )
                }

                Text(
                    text = "• Content Rating: Max G (Family-Safe)\n• Child-Directed: Enabled (COPPA)\n• Personalized Ads: Disabled (npa=1)\n• Banner ID: ${AdMobConfig.bannerAdUnitId}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TextOnSurfaceVariant,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )
                )

                Text(
                    text = "Configured centrally in com.example.ads.AdMobConfig for safe 1-step production replacement.",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OutlineColor,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}
