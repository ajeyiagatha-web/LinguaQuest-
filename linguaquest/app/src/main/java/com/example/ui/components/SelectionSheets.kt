package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LanguageCatalog
import com.example.model.LanguageOption
import com.example.model.Scenario
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelectorSheet(
    languages: List<LanguageOption>,
    selectedLanguage: LanguageOption,
    onSelectLanguage: (LanguageOption) -> Unit,
    onAddCustomLanguage: (LanguageOption) -> Unit,
    onDismiss: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    var showCustomDialog by remember { mutableStateOf(false) }

    var customLangName by remember { mutableStateOf("") }
    var customLangFlag by remember { mutableStateOf("🌐") }
    var customLangCode by remember { mutableStateOf("") }
    var customLevel by remember { mutableStateOf("A1") }

    val categories = listOf("All", "Nigerian 🇳🇬", "Popular", "African", "European", "Asian", "Middle East", "Americas")

    val popularCodes = setOf("FR", "ES", "DE", "JA", "ZH", "KO", "IT", "PT", "AR", "RU", "YO", "PCM")
    val nigerianCodes = setOf("YO", "IG", "HA", "PCM", "EDO", "EFI", "TIV", "FUL", "URH", "KAU")
    val africanCodes = setOf("YO", "IG", "HA", "PCM", "EDO", "EFI", "TIV", "FUL", "URH", "KAU", "SW", "ZU", "XH", "AM", "SO", "AF")
    val europeanCodes = setOf("FR", "ES", "DE", "IT", "PT", "NL", "RU", "PL", "SV", "NO", "DA", "FI", "EL", "TR", "UK", "CS", "HU", "RO", "GA", "CY", "GD", "HR", "BG", "CA", "EU", "IS")
    val asianCodes = setOf("JA", "ZH", "YUE", "KO", "HI", "VI", "ID", "TH", "TL", "MS", "BN", "TA", "TE", "UR", "PA")
    val middleEastCodes = setOf("AR", "HE", "FA")
    val americasCodes = setOf("PT_BR", "ES_MX", "HT", "QU", "GN")

    val filteredLanguages = remember(searchQuery, selectedCategory, languages) {
        languages.filter { lang ->
            val matchesQuery = lang.name.contains(searchQuery, ignoreCase = true) ||
                    lang.code.contains(searchQuery, ignoreCase = true) ||
                    lang.label.contains(searchQuery, ignoreCase = true)

            val matchesCategory = when (selectedCategory) {
                "All" -> true
                "Nigerian 🇳🇬" -> nigerianCodes.contains(lang.code.uppercase()) || lang.flag == "🇳🇬"
                "African" -> africanCodes.contains(lang.code.uppercase()) || lang.flag == "🇳🇬"
                "Popular" -> popularCodes.contains(lang.code.uppercase())
                "European" -> europeanCodes.contains(lang.code.uppercase())
                "Asian" -> asianCodes.contains(lang.code.uppercase())
                "Middle East" -> middleEastCodes.contains(lang.code.uppercase())
                "Americas" -> americasCodes.contains(lang.code.uppercase())
                else -> true
            }

            matchesQuery && matchesCategory
        }
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Global Course Catalog",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextOnSurface
                        )
                    )
                    Text(
                        text = "Learn any language in the world",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant)
                    )
                }

                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search any language (e.g. Japanese, Arabic, Swahili)...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = BrandPrimary)
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                singleLine = true
            )

            // Category Filter Pills
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(9999.dp))
                            .background(if (isSelected) BrandPrimary else SurfaceContainerLow)
                            .border(
                                width = 1.dp,
                                color = if (isSelected) BrandPrimary else OutlineVariantColor,
                                shape = RoundedCornerShape(9999.dp)
                            )
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = cat,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                                color = if (isSelected) Color.White else TextOnSurface
                            )
                        )
                    }
                }
            }

            // Add Custom Language Button Callout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(14.dp))
                    .background(BrandPrimaryFixed)
                    .border(1.5.dp, BrandPrimaryFixedDim, RoundedCornerShape(14.dp))
                    .clickable { showCustomDialog = true }
                    .padding(horizontal = 14.dp, vertical = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(BrandPrimary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
                    }
                    Column {
                        Text(
                            text = "Add Any Other Language",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnBrandPrimaryFixed
                            )
                        )
                        Text(
                            text = "Enter any world, regional, or classical language",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = TextOnSurfaceVariant
                            )
                        )
                    }
                }

                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = null, tint = BrandPrimary)
            }

            // Languages List
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 380.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredLanguages) { lang ->
                    val isSelected = lang.code == selectedLanguage.code
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) BrandPrimaryFixed else SurfaceContainerLowest)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) BrandPrimary else OutlineVariantColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onSelectLanguage(lang) }
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
                                Text(text = lang.flag, fontSize = 24.sp)
                                Column {
                                    Text(
                                        text = lang.name,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TextOnSurface
                                        )
                                    )
                                    Text(
                                        text = lang.label,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = TextOnSurfaceVariant,
                                            fontSize = 12.sp
                                        )
                                    )
                                }
                            }

                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .clip(CircleShape)
                                        .background(BrandPrimary),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Selected",
                                        tint = Color.White,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                if (filteredLanguages.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "No matching pre-set language found for '$searchQuery'",
                                style = MaterialTheme.typography.bodyMedium.copy(color = TextOnSurfaceVariant)
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    customLangName = searchQuery
                                    customLangCode = searchQuery.take(3).uppercase()
                                    showCustomDialog = true
                                }
                            ) {
                                Text("Add '$searchQuery' as Custom Language")
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    // Modal Dialog to Add Any Custom Language
    if (showCustomDialog) {
        AlertDialog(
            onDismissRequest = { showCustomDialog = false },
            title = {
                Text(
                    text = "Add Custom Language",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Text(
                        text = "You can learn any language! Enter its name and emoji emblem:",
                        style = MaterialTheme.typography.bodySmall.copy(color = TextOnSurfaceVariant)
                    )

                    OutlinedTextField(
                        value = customLangName,
                        onValueChange = {
                            customLangName = it
                            if (customLangCode.isEmpty() && it.isNotEmpty()) {
                                customLangCode = it.take(2).uppercase()
                            }
                        },
                        label = { Text("Language Name (e.g. Yoruba, Esperanto, Latin)") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = customLangFlag,
                            onValueChange = { customLangFlag = it },
                            label = { Text("Emoji Flag / Icon") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                        OutlinedTextField(
                            value = customLevel,
                            onValueChange = { customLevel = it },
                            label = { Text("Level (A1, A2, etc.)") },
                            singleLine = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val trimmedName = customLangName.trim()
                        if (trimmedName.isNotEmpty()) {
                            val code = (customLangCode.ifBlank { trimmedName.take(2) }).uppercase()
                            val flag = customLangFlag.ifBlank { "🌐" }
                            val newLang = LanguageOption(
                                code = code,
                                label = "$code $customLevel",
                                flag = flag,
                                name = trimmedName
                            )
                            onAddCustomLanguage(newLang)
                            showCustomDialog = false
                            onDismiss()
                        }
                    },
                    enabled = customLangName.isNotBlank()
                ) {
                    Text("Start Learning")
                }
            },
            dismissButton = {
                TextButton(onClick = { showCustomDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScenarioSelectorSheet(
    scenarios: List<Scenario>,
    currentScenario: Scenario,
    onSelectScenario: (Scenario) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = SurfaceContainerLowest,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Choose Conversation Scenario",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextOnSurface
                    )
                )
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
                }
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(scenarios) { scenario ->
                    val isSelected = scenario.id == currentScenario.id
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) BrandPrimaryFixed else SurfaceContainerLow)
                            .border(
                                width = if (isSelected) 2.dp else 1.dp,
                                color = if (isSelected) BrandPrimary else OutlineVariantColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable { onSelectScenario(scenario) }
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(text = scenario.emoji, fontSize = 26.sp)
                                Column {
                                    Text(
                                        text = scenario.title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = TextOnSurface
                                        )
                                    )
                                    Text(
                                        text = scenario.mission,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = TextOnSurfaceVariant
                                        )
                                    )
                                }
                            }

                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Selected",
                                    tint = BrandPrimary
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}
