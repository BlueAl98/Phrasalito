package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nayibit.phrasalito_presentation.composables.ProgressBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

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
                .padding(16.dp)
        ) {

            ProgressBar(
                    current = state.currentIndex,
                    total = state.totalItems,
                    modifier = Modifier.weight(0.1f)
                 )

            ExercisePager(
                modifier = Modifier.weight(0.7f),
                items = state.phrases,
                currentIndex = state.currentIndex,
                onNext = { onEvent(ExerciseUiEvent.OnNextClicked) }
            )


            Column ( modifier = Modifier.weight(0.3f)) {

                OutlinedTextField(
                    value = state.userInput,
                    onValueChange = { onEvent(ExerciseUiEvent.OnInputChanged(it)) },
                    label = { Text("Your Answer") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { onEvent(ExerciseUiEvent.OnCheckClicked) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Check Answer")
                }

            }
        }
    }
}



@Composable
fun ExercisePager(
    modifier: Modifier = Modifier,
    items: List<String>,
    currentIndex: Int,
    onNext: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        initialPage = currentIndex,
        pageCount = { items.size }
    )

    Column(
        modifier = modifier.fillMaxSize().background(Color.Red),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Pager
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.80f)
        ) { page ->
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = items[page],
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
            }
        }


        Box(modifier =
            Modifier.weight(0.20f)
                .background(Color.Green),
            contentAlignment = Alignment.Center
        ){
            Button(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < items.size) {
                        // animate scroll to next page
                        scope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                        onNext()
                    }
                    onNext
                  },
                modifier = modifier
                    .fillMaxWidth().padding(5.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Next")
            }
        }
    }
}