package com.example.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.model.LanguageOption
import com.example.ui.theme.*
import com.example.viewmodel.AppTab

const val CAMILLE_AVATAR_URL_1 = "https://lh3.googleusercontent.com/aida-public/AB6AXuAs_EvHkE6eo9DMocESG8OVB9_z-CU-Fn97JcaQ9tM9DsWJuR-i3D7ic7X7o05YxUiPyJF-IRNI5SJEC3LNTMuDLTWvcaryr7h6vfBCGVS1_X6mmIP7kOjHHV66VhLwrX0Z9ZTu94g13FJPTFebOnUu3zD8Q0C0dgTXhFfBvVY0wnyOn6vU9wfA1EPsxSlSEAJ7u-jhV9PVgApnp_uARsJwF8TnT3XbdDU93fA-gLpBwO9Uk6scnamD9w"
const val CAMILLE_AVATAR_URL_2 = "https://lh3.googleusercontent.com/aida-public/AB6AXuDvwukm4c_ThV867M79JczkIQ-VzcCm28_oA6TOsgi-nVt2QBQj0SKyo17wmcom521EMKy5YlQPN0wzgcq8GQnrRNH7qS8c0sv77zdH7S_CDo48Endt1n1vU_PpoaVMeogtvNSvhui_AxUV_B6t0Oqpl7BP8C3cvYIwywtQnlwMPIw4YkDAkyABaSEdkCJeecIhacc3RjMl379S9b-WllJ20d-SW7o4A_aNUXLMbH61t4pTi0OkAdWUVg"

/**
 * Physical 3D Tactile Button with realistic mechanical press depth
 */
@Composable
fun TactileButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = BrandPrimaryContainer,
    baseExtrusionColor: Color = BrandPrimary,
    cornerRadius: Dp = 16.dp,
    extrusionDepth: Dp = 4.dp,
    enabled: Boolean = true,
    testTag: String = "tactile_button",
    content: @Composable BoxScope.() -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val currentOffset by animateDpAsState(
        targetValue = if (isPressed && enabled) (extrusionDepth - 1.dp) else 0.dp,
        animationSpec = tween(durationMillis = 60),
        label = "button_press_offset"
    )

    val currentBottomDepth by animateDpAsState(
        targetValue = if (isPressed && enabled) 1.dp else extrusionDepth,
        animationSpec = tween(durationMillis = 60),
        label = "button_base_depth"
    )

    Box(
        modifier = modifier
            .testTag(testTag)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                onClick = onClick
            )
            .offset(y = currentOffset)
    ) {
        // Base / Extrusion Layer
        Box(
            modifier = Modifier
                .matchParentSize()
                .offset(y = currentBottomDepth)
                .background(baseExtrusionColor, RoundedCornerShape(cornerRadius))
        )

        // Top Face
        Box(
            modifier = Modifier
                .background(backgroundColor, RoundedCornerShape(cornerRadius))
                .padding(horizontal = 16.dp, vertical = 12.dp),
            contentAlignment = Alignment.Center,
            content = content
        )
    }
}

/**
 * Top App Bar with brand, language picker chip, streak quick action,
 * and Persistent Gamification Stats Bar (Streaks, Lingots, Hearts)
 */
@Composable
fun LinguaTopAppBar(
    selectedLanguage: LanguageOption,
    streaks: Int,
    gems: Int,
    hearts: Int,
    onLanguageClick: () -> Unit,
    onStreakClick: () -> Unit
) {
    Surface(
        color = SurfaceContainerLowest,
        shadowElevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = OutlineVariantColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            // Row 1: Brand & Language Switcher & Fire Action
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Logo Box
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(BrandPrimaryContainer, RoundedCornerShape(12.dp))
                            .border(width = 1.5.dp, color = BrandPrimary, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Translate,
                            contentDescription = "LinguaQuest Logo",
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // App Title
                    Text(
                        text = "LinguaQuest",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = BrandPrimary
                        )
                    )

                    // Language Selector Chip
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(SurfaceContainerLow)
                            .border(width = 1.5.dp, color = OutlineVariantColor, RoundedCornerShape(9999.dp))
                            .clickable(onClick = onLanguageClick)
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = selectedLanguage.flag, fontSize = 14.sp)
                        Text(
                            text = selectedLanguage.label,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.ExtraBold,
                                color = TextOnSurface
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Switch Language",
                            tint = TextOnSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // Streaks Fire Button
                IconButton(
                    onClick = onStreakClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.LocalFireDepartment,
                        contentDescription = "Streak Fire Action",
                        tint = BrandTertiaryFixedDim,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Row 2: Persistent Gamification Stats Bar (Streaks, Gems, Hearts)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Streak Fire Badge
                StatBadge(
                    icon = Icons.Filled.LocalFireDepartment,
                    value = streaks.toString(),
                    iconTint = StreakRed,
                    textColor = StreakRed,
                    bgColor = StreakBg,
                    borderColor = StreakBorder,
                    shadowColor = StreakShadow,
                    onClick = onStreakClick
                )

                // Lingots / Gems Badge
                StatBadge(
                    icon = Icons.Filled.Diamond,
                    value = gems.toString(),
                    iconTint = GemGold,
                    textColor = GemGold,
                    bgColor = GemBg,
                    borderColor = GemBorder,
                    shadowColor = GemShadow
                )

                // Hearts Metric Badge
                StatBadge(
                    icon = Icons.Filled.Favorite,
                    value = hearts.toString(),
                    iconTint = HeartPink,
                    textColor = HeartPink,
                    bgColor = HeartBg,
                    borderColor = HeartBorder,
                    shadowColor = HeartShadow
                )
            }
        }
    }
}

@Composable
fun StatBadge(
    icon: ImageVector,
    value: String,
    iconTint: Color,
    textColor: Color,
    bgColor: Color,
    borderColor: Color,
    shadowColor: Color,
    onClick: (() -> Unit)? = null
) {
    val clickModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Box(
        modifier = Modifier
            .background(shadowColor, RoundedCornerShape(9999.dp))
            .padding(bottom = 2.dp)
            .then(clickModifier)
    ) {
        Row(
            modifier = Modifier
                .background(bgColor, RoundedCornerShape(9999.dp))
                .border(width = 1.5.dp, color = borderColor, RoundedCornerShape(9999.dp))
                .padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = textColor
                )
            )
        }
    }
}

/**
 * Bottom Navigation Bar matching both Image 1 and Image 3
 */
@Composable
fun LinguaBottomNavBar(
    currentTab: AppTab,
    onTabSelected: (AppTab) -> Unit
) {
    Surface(
        color = SurfaceContainerLowest,
        shadowElevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = OutlineVariantColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BottomNavItem(
                icon = Icons.Default.Route,
                label = "Learn",
                isSelected = currentTab == AppTab.LEARN,
                onClick = { onTabSelected(AppTab.LEARN) }
            )
            BottomNavItem(
                icon = Icons.Default.Mic,
                label = "Review",
                isSelected = currentTab == AppTab.REVIEW,
                onClick = { onTabSelected(AppTab.REVIEW) }
            )
            BottomNavItem(
                icon = Icons.Default.SmartToy,
                label = "AI Tutor",
                isSelected = currentTab == AppTab.AI_TUTOR,
                onClick = { onTabSelected(AppTab.AI_TUTOR) }
            )
            BottomNavItem(
                icon = Icons.Default.EmojiEvents,
                label = "Leaderboard",
                isSelected = currentTab == AppTab.LEADERBOARD,
                onClick = { onTabSelected(AppTab.LEADERBOARD) }
            )
            BottomNavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = currentTab == AppTab.PROFILE,
                onClick = { onTabSelected(AppTab.PROFILE) }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    if (isSelected) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(BrandPrimaryFixed)
                .border(width = 1.dp, color = BrandPrimary, RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 14.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = OnBrandPrimaryFixed,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnBrandPrimaryFixed
                )
            )
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .clickable(onClick = onClick)
                .padding(horizontal = 10.dp, vertical = 6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = TextOnSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Medium,
                    color = TextOnSurfaceVariant
                )
            )
        }
    }
}

/**
 * Camille's 3D Avatar with online status indicator
 */
@Composable
fun CamilleAvatar(
    avatarUrl: String = CAMILLE_AVATAR_URL_1,
    size: Dp = 48.dp,
    showOnlineStatus: Boolean = true
) {
    val context = LocalContext.current
    Box(modifier = Modifier.size(size)) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Camille AI Tutor Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
                .clip(CircleShape)
                .border(2.dp, BrandPrimaryContainer, CircleShape)
        )

        if (showOnlineStatus) {
            Box(
                modifier = Modifier
                    .size(size * 0.28f)
                    .align(Alignment.BottomEnd)
                    .background(BrandSecondaryContainer, CircleShape)
                    .border(2.dp, SurfaceContainerLowest, CircleShape)
            )
        }
    }
}
