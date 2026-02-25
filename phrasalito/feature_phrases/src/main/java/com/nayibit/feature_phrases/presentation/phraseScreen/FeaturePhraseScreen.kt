package com.nayibit.feature_phrases.presentation.phraseScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeaturePhraseScreen(
    navigation: (Int) -> Unit
){
    val  vm: PhraseViewModel = hiltViewModel()
    val state by vm.state.collectAsStateWithLifecycle()

    PhraseScreen(
        state = state,
        eventFlow = vm.eventFlow,
        onEvent = vm::onEvent,
    ) { id, lngCode ->
       navigation(id)
    }

}