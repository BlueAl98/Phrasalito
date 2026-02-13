package com.ie.feature_startscreen.presentation.startScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeatureStartScreen(
    navigation: () -> Unit
){


    val viewModel: StartViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    StartScreen(
        state = state,
        eventFlow = viewModel.eventFlow,
        onEvent = viewModel::onEvent
    ) {
       navigation()
    }
}