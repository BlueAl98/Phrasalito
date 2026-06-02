package com.ie.feature_startscreen.presentation.startScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nayibit.utils.SelectScreen

@Composable
fun FeatureStartScreen(
    navigation: (SelectScreen) -> Unit
){

    val viewModel: StartViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StartScreen(
        state = state,
        eventFlow = viewModel.eventFlow,
        onEvent = viewModel::onEvent
    ) { screen->
       navigation(screen)
    }
}