package com.nayibit.phrasalito_presentation.screens.exerciseScreen


import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.HighlightedText
import com.nayibit.phrasalito_presentation.composables.IconPopover
import com.nayibit.phrasalito_presentation.composables.LoadingScreen
import com.nayibit.phrasalito_presentation.composables.ProgressBar
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import kotlinx.coroutines.flow.Flow

@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    state: ExerciseUiState,
    eventFlow: Flow<ExerciseUiEvent>,
    onEvent: (ExerciseUiEvent) -> Unit,
    navigation: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val pagerState = rememberPagerState(
        initialPage = state.currentIndex,
        pageCount = { state.phrases.size }
    )

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
                is ExerciseUiEvent.OnStartClicked -> {

                }

                else -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->

        if (state.isLoading)
            LoadingScreen()
       else if (state.phrases.isEmpty())
           Box(modifier = modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
               Text(text = "No Phrases Found")
           }
       else{
          Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            ProgressBar(
                current = state.currentIndex +1,
                total = state.totalItems,
                modifier = modifier.weight(0.1f)
            )

            ExercisePager(
                modifier = modifier.weight(0.7f),
                onEvent = onEvent,
                state = state,
                pagerState = pagerState
            )

        }
    }
}
}



@Composable
fun ExercisePager(
    modifier: Modifier = Modifier,
    onEvent: (ExerciseUiEvent) -> Unit,
    state: ExerciseUiState,
    pagerState: PagerState
) {

    LaunchedEffect(state.currentIndex) {
        pagerState.animateScrollToPage(state.currentIndex)
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Pager
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.70f)
        ) { page ->
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(
                        width = 2.dp,
                        color = if (state.phrases[page].isComplete) Color.Green else Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Box {
                    Row (modifier.fillMaxWidth().padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                        IconPopover(
                            icon = Icons.Default.Info,
                            expandedState = state.popOverState,
                            updateExpandedState = { onEvent(ExerciseUiEvent.UpdateExpandedState(it)) }
                        ) {
                            Text(
                                state.phrases[page].translation,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        if (state.phrases[page].isComplete)
                            IconPopover(
                             icon = Icons.Default.PlayArrow,
                               expandedState = state.popOverState,
                              onClick = {
                                onEvent(ExerciseUiEvent.OnSpeakPhrase(state.phrases[page].correctAnswer))
                                 })
                              }
                          }

                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HighlightedText(
                        fullText = state.phrases[page].example,
                        highlightWords = state.phrases[page].targetLanguage.split(" ")
                    )
                }
            }
        }

        Column ( modifier = Modifier.weight(0.3f).padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
            ) {
            TextFieldBase(
                enabled = !state.phrases[pagerState.currentPage].isComplete,
                value = state.inputAnswer,
                onValueChange = { onEvent(ExerciseUiEvent.OnInputChanged(it, pagerState.currentPage)) },
                label = stringResource(R.string.label_answer_phrase)
            )

            Spacer(modifier = Modifier.height(16.dp))


           ButtonBase(
                enabled = state.phrases[pagerState.currentPage].isComplete,
                onClick = {
                   onEvent(ExerciseUiEvent.OnNextPhrase)
                },
                text = stringResource(R.string.btn_next_phrase))

        }
    }
}