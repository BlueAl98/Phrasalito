package com.nayibit.feature_languages.presentation.languageScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeatureLanguageScreen(
    onLanguageSelected: (String) -> Unit
) {
    val vm: LanguageViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()

    LanguageScreen(
        state = state,
        eventFlow = vm.eventFlow,
        onEvent = vm::onEvent,
        onLanguageSelected = onLanguageSelected
    )
}
