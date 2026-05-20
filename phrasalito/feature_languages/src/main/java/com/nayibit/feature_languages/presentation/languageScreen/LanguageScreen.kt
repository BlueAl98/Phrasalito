package com.nayibit.feature_languages.presentation.languageScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.feature_languages.domain.model.LanguageStatus
import com.nayibit.feature_languages.domain.model.LanguageUi
import kotlinx.coroutines.flow.Flow

private val ColorBackground = Color(0xFF131313)
private val ColorSurface = Color(0xFF1B1B1B)
private val ColorPrimary = Color(0xFFB0C6FF)
private val ColorPrimaryContainer = Color(0xFF1D66DB)
private val ColorOnSurface = Color(0xFFE2E2E2)
private val ColorOnSurfaceVariant = Color(0xFFC2C6D6)

@Composable
fun LanguageScreen(
    state: LanguageStateUi,
    eventFlow: Flow<LanguageUiEvent>,
    onEvent: (LanguageUiEvent) -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is LanguageUiEvent.NavigateWithLanguage -> onLanguageSelected(event.languageCode)
                is LanguageUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                else -> {}
            }
        }
    }

    Scaffold(
        containerColor = ColorBackground,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp)
        ) {
            Text(
                text = "Elige el idioma a aprender",
                color = ColorOnSurface,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 36.sp
            )

            Spacer(modifier = Modifier.height(32.dp))

            SectionLabel("Idiomas disponibles")

            Spacer(modifier = Modifier.height(12.dp))

            AvailableLanguagesGrid(
                languages = state.availableLanguages,
                onSelect = { onEvent(LanguageUiEvent.SelectLanguage(it)) }
            )

            Spacer(modifier = Modifier.height(32.dp))

            SectionLabel("Explorar nuevos")

            Spacer(modifier = Modifier.height(12.dp))

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                state.explorableLanguages.forEach { language ->
                    ExplorableLanguageItem(
                        language = language,
                        onClick = {
                            if (language.status == LanguageStatus.DOWNLOADABLE) {
                                onEvent(LanguageUiEvent.DownloadLanguage(language))
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = ColorOnSurface,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 0.6.sp
    )
}

@Composable
private fun AvailableLanguagesGrid(
    languages: List<LanguageUi>,
    onSelect: (LanguageUi) -> Unit
) {
    val rows = languages.chunked(2)
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        rows.forEach { rowItems ->
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                rowItems.forEach { language ->
                    AvailableLanguageCard(
                        language = language,
                        modifier = Modifier.weight(1f),
                        onClick = { onSelect(language) }
                    )
                }
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun AvailableLanguageCard(
    language: LanguageUi,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(ColorSurface)
            .clickable(onClick = onClick)
            .padding(vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = language.flagEmoji,
                fontSize = 36.sp
            )
            Text(
                text = language.displayName,
                color = ColorOnSurface,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun ExplorableLanguageItem(
    language: LanguageUi,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(ColorSurface)
            .clickable(
                enabled = language.status == LanguageStatus.DOWNLOADABLE,
                onClick = onClick
            )
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(ColorSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = ColorPrimary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = language.displayName,
                color = ColorOnSurface,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = when (language.status) {
                    LanguageStatus.DOWNLOADABLE -> "Disponible para descargar"
                    LanguageStatus.COMING_SOON -> "Próximamente"
                    else -> ""
                },
                color = ColorOnSurfaceVariant,
                fontSize = 12.sp
            )
        }

        Icon(
            imageVector = when (language.status) {
                LanguageStatus.DOWNLOADABLE -> Icons.Default.FileDownload
                else -> Icons.Default.Schedule
            },
            contentDescription = null,
            tint = when (language.status) {
                LanguageStatus.DOWNLOADABLE -> ColorPrimary
                else -> ColorOnSurfaceVariant
            },
            modifier = Modifier.size(20.dp)
        )
    }
}
