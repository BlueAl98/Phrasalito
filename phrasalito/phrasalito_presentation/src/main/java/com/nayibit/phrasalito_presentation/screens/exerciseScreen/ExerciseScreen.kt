package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow

@Composable
fun ExerciseScreen(
    state: ExerciseUiState,
    eventFlow: Flow<ExerciseUiEvent>,
    onEvent: (ExerciseUiEvent) -> Unit,
    navigation: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // Collect events lifecycle-aware
    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is ExerciseUiEvent.ShowToast -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is ExerciseUiEvent.NavigateNext -> {
                    navigation()
                }
                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = state.title.ifEmpty { "Welcome to Exercise" })
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = { onEvent(ExerciseUiEvent.OnStartClicked) }) {
                Text("Start Exercise")
            }
        }
    }
}