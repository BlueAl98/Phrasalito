package com.nayibit.phrasalito_presentation.screens.exerciseScreen


import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.CircularProgressIndicator
import com.nayibit.phrasalito_presentation.composables.HighlightedText
import com.nayibit.phrasalito_presentation.composables.IconPopover
import com.nayibit.phrasalito_presentation.composables.LoadingScreen
import com.nayibit.phrasalito_presentation.composables.ProgressBar
import com.nayibit.phrasalito_presentation.composables.SimpleConfirmDialog
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExerciseScreen(
    modifier: Modifier = Modifier,
    state: ExerciseUiState,
    eventFlow: Flow<ExerciseUiEvent>,
    onEvent: (ExerciseUiEvent) -> Unit,
    navigation: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val sheetState = rememberBottomSheetScaffoldState()
    val orientation = LocalConfiguration.current.orientation


    val pagerState = rememberPagerState(
        initialPage = state.currentIndex,
        pageCount = { state.phrases.size }
    )

    BackHandler {
        if (state.testCompleted) {
          onEvent(ExerciseUiEvent.NavigateNext)
        }else{
         onEvent(ExerciseUiEvent.ShowDialog(true, BodyModalExercise.BODY_EXIT_SCREEN))
        }

    }

    if (state.showDialog){
        SimpleConfirmDialog(
            title = if (state.bodyModalExercise == BodyModalExercise.BODY_SKIP_QUESTION) stringResource(R.string.title_skip_question) else stringResource(R.string.title_exit_screen),
            onConfirm = {
                if (state.bodyModalExercise == BodyModalExercise.BODY_SKIP_QUESTION)
                     onEvent(ExerciseUiEvent.ShowAllInfo(state.currentIndex))
                else {
                    onEvent(ExerciseUiEvent.ShowDialog(false, BodyModalExercise.BODY_EXIT_SCREEN))
                    onEvent(ExerciseUiEvent.NavigateNext)
                }
                        },
            onCancel = { onEvent(ExerciseUiEvent.ShowDialog(false, BodyModalExercise.BODY_SKIP_QUESTION)) }
        )
    }

    // Collect events lifecycle-aware
    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is ExerciseUiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(event.message)
                }
                is ExerciseUiEvent.NavigateNext -> {
                    navigation()
                }
                is ExerciseUiEvent.OnNextPhrase ->{
                    sheetState.bottomSheetState.expand()
                }
                else -> Unit
            }
        }
    }

    BottomSheetScaffold(
        sheetPeekHeight = 0.dp,
        sheetSwipeEnabled = false,
        scaffoldState = sheetState,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        sheetContent = {
            Column (modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth()
                .padding(bottom = 20.dp)){

              when (orientation) {
                  Configuration.ORIENTATION_LANDSCAPE -> BottomSheetContentLandScape(
                      state = state,
                      onEvent = onEvent
                  )
                  else -> BottomSheetContentPortrait(
                      state = state,
                      onEvent = onEvent
                  )
              }
            }

        }
    ) { padding ->

       Box(
            modifier
                .fillMaxSize()
                .padding(padding)
        ) {
           if (state.isLoading)
               LoadingScreen()
           else if (state.phrases.isEmpty())
               Box(contentAlignment = Alignment.Center, modifier = modifier.fillMaxSize()) {
                   Text(text = "No Phrases Found")
          }
            else {

                when (orientation) {
                    Configuration.ORIENTATION_LANDSCAPE -> {
                        LandscapeContent(
                            state = state,
                            onEvent = onEvent,
                            pagerState = pagerState)
                    }
                    else -> {
                        PortaitContent(
                            state = state,
                            onEvent = onEvent,
                            pagerState = pagerState)
                    }
                }


            }
        }
    }
}

@Composable
fun PortaitContent(
    modifier: Modifier = Modifier,
    state: ExerciseUiState,
    onEvent: (ExerciseUiEvent) -> Unit,
    pagerState: PagerState
){
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        ProgressBar(
            current = state.currentIndex + 1,
            total = state.totalItems,
            modifier = modifier.weight(0.1f)
        )
        ExercisePager(
            modifier = modifier.weight(0.7f),
            onEvent = onEvent,
            state = state,
            pagerState = pagerState
        )

        Column ( modifier = Modifier
            .weight(0.3f)
            .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            TextFieldBase(
                enabled = state.phrases[pagerState.currentPage].phraseState == PhraseState.NOT_STARTED,
                value = state.inputAnswer,
                onValueChange = { onEvent(ExerciseUiEvent.OnInputChanged(it, pagerState.currentPage)) },
                label = stringResource(R.string.label_answer_phrase)
            )

            Spacer(modifier = Modifier.height(16.dp))


            ButtonBase(
                enabled = state.phrases[pagerState.currentPage].phraseState != PhraseState.NOT_STARTED,
                onClick = {
                    onEvent(ExerciseUiEvent.OnNextPhrase(pagerState.currentPage))
                },
                text = stringResource(R.string.btn_next_phrase))

        }

    }
}

@Composable
fun LandscapeContent(
    modifier: Modifier = Modifier,
    state: ExerciseUiState,
    onEvent: (ExerciseUiEvent) -> Unit,
    pagerState: PagerState
){

        Row(modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(0.4f)
                    .fillMaxHeight()
                    .padding(horizontal = 8.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.SpaceEvenly

            ) {
                Box(modifier.weight(0.15f)) {
                ProgressBar(
                    current = state.currentIndex + 1,
                    total = state.totalItems
                )
            }

                Column(modifier.weight(0.85f), verticalArrangement = Arrangement.Center) {
                    TextFieldBase(
                        enabled = state.phrases[pagerState.currentPage].phraseState == PhraseState.NOT_STARTED,
                        value = state.inputAnswer,
                        onValueChange = {
                            onEvent(
                                ExerciseUiEvent.OnInputChanged(
                                    it,
                                    pagerState.currentPage
                                )
                            )
                        },
                        label = stringResource(R.string.label_answer_phrase)
                    )

                    Spacer(modifier = Modifier.height(16.dp))


                    ButtonBase(
                        enabled = state.phrases[pagerState.currentPage].phraseState != PhraseState.NOT_STARTED,
                        onClick = {
                            onEvent(ExerciseUiEvent.OnNextPhrase(pagerState.currentPage))
                        },
                        text = stringResource(R.string.btn_next_phrase)
                    )
                }
            }

            ExercisePager(
                modifier = modifier.weight(0.6f),
                onEvent = onEvent,
                state = state,
                pagerState = pagerState
            )

        }
    }




@Composable
fun ExercisePager(
    modifier: Modifier = Modifier,
    onEvent: (ExerciseUiEvent) -> Unit,
    state: ExerciseUiState,
    pagerState: PagerState
) {

    val colorManager =  when (state.phrases[pagerState.currentPage].phraseState) {
        PhraseState.NOT_STARTED -> Color.Transparent
        PhraseState.ERROR_ANSWER -> Color.Red
        PhraseState.COMPLETED -> Color.Green
    }

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
                        color = colorManager,
                        shape = RoundedCornerShape(16.dp)
                    ),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {

                Box {
                    Row (modifier
                        .fillMaxWidth()
                        .padding(5.dp), horizontalArrangement = Arrangement.SpaceBetween) {

                    Row {
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

                        IconPopover(
                            enabled = state.phrases[page].phraseState == PhraseState.NOT_STARTED,
                            icon = Icons.Default.QuestionMark,
                            onClick = {onEvent(ExerciseUiEvent.ShowDialog(true, BodyModalExercise.BODY_SKIP_QUESTION))}
                        )
                    }

                        if (state.phrases[page].phraseState != PhraseState.NOT_STARTED)
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


    }
}


@Composable
fun BottomSheetContentLandScape(
    modifier: Modifier = Modifier,
    state: ExerciseUiState,
    onEvent: (ExerciseUiEvent) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left side - Progress indicator
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxHeight(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = state.testProgressCorrectAnswers,
                sizePercentage = 1f, // Larger in landscape
                strokeWidthPercentage = 0.08f,
                textSizePercentage = 0.20f
            )
        }

        // Right side - Text and button
        Column(
            modifier = Modifier
                .weight(0.6f)
                .fillMaxHeight()
                .padding(start = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.label_percentage_correct_answers),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Red
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Respuestas correctas: ${state.phrases.filter { it.phraseState == PhraseState.COMPLETED }.size } /  ${state.totalItems}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(24.dp))

            ButtonBase(
                onClick = { onEvent(ExerciseUiEvent.NavigateNext) },
                text = stringResource(R.string.btn_finish_exercise),
                modifier = Modifier.fillMaxWidth(0.8f) // 80% width for better proportions
            )
        }
    }
}



@Composable
fun BottomSheetContentPortrait(
    modifier: Modifier = Modifier,
    state: ExerciseUiState,
    onEvent: (ExerciseUiEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            progress = state.testProgressCorrectAnswers,
            sizePercentage = 0.5f, // 50% of available space
            strokeWidthPercentage = 0.08f, // 6% of circle size for stroke
            textSizePercentage = 0.20f // 12% of circle size for text
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.label_percentage_correct_answers),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = Color.Red
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Respuestas correctas: ${state.phrases.filter { it.phraseState == PhraseState.COMPLETED }.size } /  ${state.totalItems}",
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonBase(
            onClick = { onEvent(ExerciseUiEvent.NavigateNext) },
            text = stringResource(R.string.btn_finish_exercise),
            modifier = Modifier.padding(horizontal = 16.dp)
        )
    }
}





