package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.screens.startScreen.Language
import com.nayibit.phrasalito_presentation.screens.startScreen.StartStateUi
import com.nayibit.phrasalito_presentation.screens.startScreen.StartUiEvent
import com.nayibit.phrasalito_presentation.ui.theme.BlueOnPrimary
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart


@Composable
fun LanguageSelectionTap(
    modifier: Modifier = Modifier,
    onLanguageSelected: (Language) -> Unit = {},
    state: StartStateUi,
    onEvent: (StartUiEvent) -> Unit
) {

    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = state.languageListScrollIndex,
        initialFirstVisibleItemScrollOffset = state.languageListScrollOffset
    )

    LaunchedEffect(listState) {
        snapshotFlow {
            Pair(listState.firstVisibleItemIndex, listState.firstVisibleItemScrollOffset)
        }.collect { (index, offset) ->
            onEvent(StartUiEvent.SetScrollPosition(index, offset))
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Top App Bar
        TopAppBar()

        // Scrollable Language List
        Column(
            modifier = modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp, bottom = 16.dp)
        ) {
            LazyColumn (
                state = listState
            ) {
                items (state.languages, key = {it.id}){ language ->
                    LanguageRadioItem(
                        language = language,
                        isSelected = state.currentLanguage == language,
                        onSelect = {
                       //     state.currentLanguage = language
                            onLanguageSelected(language)
                        },
                        modifier = Modifier.padding(bottom = 12.dp)
                    )
                }

            }

        }
    }
}

@Composable
private fun TopAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Text(
            text = "Elige tu idioma a aprender",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
    HorizontalDivider()
}

@Composable
private fun LanguageRadioItem(
    language: Language,
    isSelected: Boolean,
    onSelect: (Language) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .selectable(
                selected = isSelected,
                onClick = { onSelect(language) },
                role = Role.RadioButton
            ),
        //color = ,
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(
            width = 1.dp,
            color = if (isSelected) primaryGradientStart else BlueOnPrimary
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Radio Button
            RadioButton(
                selected = isSelected,
                onClick = { onSelect(language) },
                colors = RadioButtonDefaults.colors(
                    selectedColor = primaryGradientStart,
                    unselectedColor = Color(0xFFD1D5DB)
                ),
                modifier = Modifier.size(20.dp)
            )

            // Language Name
            Text(
                text = language.language,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}


