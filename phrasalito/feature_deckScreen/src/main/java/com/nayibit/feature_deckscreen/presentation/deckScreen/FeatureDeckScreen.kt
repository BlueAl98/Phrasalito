package com.nayibit.feature_deckscreen.presentation.deckScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeatureDeckScreen() {
    val viewModel: DeckViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    DeckScreen(
        state = state,
        eventFlow = viewModel.eventFlow,
        onEvent = viewModel::onEvent,
        navigationToPhrases = { idDeck, lngCode ->
        }
    )

}