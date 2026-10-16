package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.GuidebookEntry
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GuidebookSheet(
    unitTitle: String = "Bonjour & Basics",
    entries: List<GuidebookEntry> = emptyList(),
    onDismiss: () -> Unit,
    onPlayAudio: (String) -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = BrandPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "$unitTitle Guidebook",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        )
                    )
                }
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            Text(
                text = "Key phrases, pronunciation notes, and grammar tips for this course.",
                style = MaterialTheme.typography.bodyMedium.copy(color = TextOnSurfaceVariant)
            )

            if (entries.isNotEmpty()) {
                entries.forEach { entry ->
                    GuideSectionCard(
                        title = entry.title,
                        description = entry.description
                    ) {
                        entry.phrases.forEach { (targetText, translation) ->
                            PhraseRow(targetText, translation, onPlayAudio)
                        }
                    }
                }
            } else {
                // Fallback section
                GuideSectionCard(
                    title = "1. Essential Greetings",
                    description = "Polite expressions are the foundation of any language."
                ) {
                    PhraseRow("Hello", "Greeting", onPlayAudio)
                    PhraseRow("Please", "Polite request", onPlayAudio)
                    PhraseRow("Thank you", "Gratitude", onPlayAudio)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun GuideSectionCard(
    title: String,
    description: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceContainerLow)
            .border(1.5.dp, SurfaceContainerHighest, RoundedCornerShape(16.dp))
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold,
                color = BrandPrimary
            )
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant)
        )
        HorizontalDivider(color = OutlineVariantColor.copy(alpha = 0.5f), modifier = Modifier.padding(vertical = 4.dp))
        content()
    }
}

@Composable
fun PhraseRow(
    french: String,
    english: String,
    onPlayAudio: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = french,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = TextOnSurface
                )
            )
            Text(
                text = english,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = TextOnSurfaceVariant,
                    fontSize = 12.sp
                )
            )
        }

        IconButton(
            onClick = { onPlayAudio(french) },
            modifier = Modifier.size(28.dp)
        ) {
            Icon(
                imageVector = Icons.Default.VolumeUp,
                contentDescription = "Pronounce",
                tint = BrandPrimary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}
