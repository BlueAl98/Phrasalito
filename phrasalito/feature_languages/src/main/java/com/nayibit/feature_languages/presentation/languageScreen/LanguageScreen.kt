package com.nayibit.feature_languages.presentation.languageScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nayibit.feature_languages.R
import com.nayibit.feature_languages.domain.model.LanguageStatus
import com.nayibit.feature_languages.domain.model.LanguageUi
import com.nayibit.utils.ui.composables.BaseDialog
import com.nayibit.utils.ui.composables.LoadingScreen
import kotlinx.coroutines.flow.Flow

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

    BaseDialog(
        showDialog = state.showErrorDialog,
        offsideDismiss = false,
        onDismissRequest = { onEvent(LanguageUiEvent.DismissErrorDialog) }
    ) {
        Text(
            text = stringResource(R.string.error_network_title),
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = state.errorMessage ?: stringResource(R.string.error_network_default),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(onClick = { onEvent(LanguageUiEvent.DismissErrorDialog) }) {
                Text(stringResource(R.string.action_dismiss))
            }
            Button(onClick = { onEvent(LanguageUiEvent.RetryLoad) }) {
                Text(stringResource(R.string.action_retry))
            }
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        if (state.isLoading)
            LoadingScreen()
        else
          LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Text(
                        text = stringResource(R.string.title_choose_language),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    SectionLabel(stringResource(R.string.label_available_languages))
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            items(state.availableLanguages, key = { it.code }) { language ->
                AvailableLanguageCard(
                    language = language,
                    onClick = { onEvent(LanguageUiEvent.SelectLanguage(language)) }
                )
            }

            item(span = { GridItemSpan(maxLineSpan) }) {
                Column {
                    Spacer(modifier = Modifier.height(20.dp))
                    SectionLabel(stringResource(R.string.label_explore_new))
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }

            items(
                items = state.explorableLanguages,
                key = { it.code },
                span = { GridItemSpan(maxLineSpan) }
            ) { language ->
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

@Composable
private fun SectionLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onSurface,
        style = MaterialTheme.typography.labelLarge
    )
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
            .background(MaterialTheme.colorScheme.secondary)
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
                style = MaterialTheme.typography.displaySmall
            )
            Text(
                text = language.displayName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
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
            .background(MaterialTheme.colorScheme.secondary)
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
                .background(MaterialTheme.colorScheme.onPrimary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = language.displayName,
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.labelLarge
            )
            Text(
                text = when (language.status) {
                    LanguageStatus.DOWNLOADABLE -> stringResource(R.string.label_available_to_download)
                    LanguageStatus.COMING_SOON -> stringResource(R.string.label_coming_soon)
                    else -> ""
                },
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                style = MaterialTheme.typography.labelMedium
            )
        }

        Icon(
            imageVector = when (language.status) {
                LanguageStatus.DOWNLOADABLE -> Icons.Default.FileDownload
                else -> Icons.Default.Schedule
            },
            contentDescription = null,
            tint = when (language.status) {
                LanguageStatus.DOWNLOADABLE -> MaterialTheme.colorScheme.primary
                else -> MaterialTheme.colorScheme.onSurfaceVariant
            },
            modifier = Modifier.size(20.dp)
        )
    }
}
