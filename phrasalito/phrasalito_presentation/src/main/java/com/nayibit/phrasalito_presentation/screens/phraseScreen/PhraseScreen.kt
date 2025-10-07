package com.nayibit.phrasalito_presentation.screens.phraseScreen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nayibit.common.util.UiText
import com.nayibit.common.util.asString
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.AdaptiveLanguageCard
import com.nayibit.phrasalito_presentation.composables.BaseDialog
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import com.nayibit.phrasalito_presentation.composables.isLandscape
import com.spartapps.swipeablecards.state.rememberSwipeableCardsState
import com.spartapps.swipeablecards.ui.SwipeableCardDirection
import com.spartapps.swipeablecards.ui.lazy.LazySwipeableCards
import com.spartapps.swipeablecards.ui.lazy.items
import kotlinx.coroutines.flow.Flow


@Composable
fun PhraseScreen(
    modifier: Modifier = Modifier,
    state: PhraseStateUi,
    eventFlow: Flow<PhraseUiEvent>,
    onEvent: (PhraseUiEvent) -> Unit,
    navigation: (id: Int) -> Unit
    )
{
      val context = LocalContext.current

      LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is PhraseUiEvent.ShowToast -> {
                    val message = event.message.asString(context)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
     }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvent(PhraseUiEvent.ShowModal(BodyModalEnum.BODY_INSERT_PHRASE))
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
            }
        },
        content = { padding ->
            Box(modifier
                .fillMaxSize()
                .padding(padding)) {
                when {
                    state.isLoading -> {
                        Box(modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier.align(Alignment.Center))
                        }
                    }

                    state.phrases.isNotEmpty() -> {

                        key (state.phrases) {
                            AnimatedVisibility(
                                visible = true,
                                enter = fadeIn(tween(500)) + slideInHorizontally(initialOffsetX = { it / 2 }),
                                exit = fadeOut(tween(200))
                            ) {
                                AreaStudyCards(
                                    modifier = modifier,
                                    state = state,
                                    onEvent = onEvent
                                )
                            }
                        }
                    }
                    else -> Text(text = stringResource(R.string.label_dont_have_phrases), modifier.align(Alignment.Center))
                }

                BaseDialog(
                    showDialog = state.showModal,
                    offsideDismiss = false
                ) {
                    when (state.bodyModal) {
                        BodyModalEnum.BODY_INSERT_PHRASE -> BodyModalInsertPhrase(state, onEvent)
                        BodyModalEnum.BODY_UPDATE_PHRASE -> BodyModalUpdatePhrase(state, onEvent)
                        BodyModalEnum.BODY_DELETE_PHRASE -> BodyModalDeletePhrase(state, onEvent)
                    }

                }

            }
        })
}



@Composable
fun BodyModalInsertPhrase(
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit
){
    TextFieldBase(
        value = state.firstPhrase,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextFirstPhrase(it))},
        label  = stringResource(R.string.label_phrase))

    TextFieldBase(
        value = state.translation,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextTraslation(it))},
        label  = stringResource(R.string.label_traduction_phrase))

    TextFieldBase(
        value = state.example,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextExample(it))},
        label  = stringResource(R.string.label_example_phrase))

    ButtonBase(
        text = stringResource(id = R.string.btn_save),
        onClick = { if (state.firstPhrase.isNotEmpty() && state.translation.isNotEmpty()) onEvent(PhraseUiEvent.InsertPhrase) else onEvent(PhraseUiEvent.ShowToast(
            UiText.StringResource(R.string.label_emty_fields))) },
        loading = state.isLoadingButton
    )
    ButtonBase(
        text = stringResource(id = R.string.btn_cancel),
        onClick = { onEvent(PhraseUiEvent.DismissModal) },
        enabled = !state.isLoadingButton
    )
}


@Composable
fun BodyModalUpdatePhrase(
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit
){
    TextFieldBase(
        value = state.firstPhrase,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextFirstPhrase(it))},
        label  = stringResource(R.string.label_phrase))

    TextFieldBase(
        value = state.translation,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextTraslation(it))},
        label  = stringResource(R.string.label_traduction_phrase))


    TextFieldBase(
        value = state.example,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextExample(it))},
        label  = stringResource(R.string.label_example_phrase))


    ButtonBase(
        text = stringResource(id = R.string.btn_update),
        onClick = { if (state.firstPhrase.isNotEmpty() && state.translation.isNotEmpty()) onEvent(PhraseUiEvent.UpdatePhrase(state.phraseToUpdate!!)) else onEvent(PhraseUiEvent.ShowToast(
            UiText.StringResource(R.string.label_emty_fields)
        )) },
        loading = state.isLoadingButton
    )
    ButtonBase(
        text = stringResource(id = R.string.btn_cancel),
        onClick = { onEvent(PhraseUiEvent.DismissModal) },
        enabled = !state.isLoadingButton
    )
}

@Composable
fun BodyModalDeletePhrase(
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit
){
    Text(text = stringResource(R.string.title_delete_phrase))

    ButtonBase(
        text = stringResource(R.string.btn_delete),
        onClick = { onEvent(PhraseUiEvent.DeletePhrase(state.phraseToUpdate!!.id)) },
        loading = state.isLoadingButton
    )
    ButtonBase(
        text = stringResource(R.string.btn_cancel),
        onClick = { onEvent(PhraseUiEvent.DismissModal) },
        enabled = !state.isLoadingButton
    )
}


@Composable
@Preview
fun AreaStudyCardsPreview(){

    val state = PhraseStateUi(
        phrases = listOf(
            PhraseUi(
                id = 1,
                targetLanguage = "Hola",
                translation = "Hello",
                example = "¡Hola! ¿Cómo estás?"
                ),

            PhraseUi(
                id = 1,
                targetLanguage = "Hola",
                translation = "Hello",
                example = "¡Hola! ¿Cómo estás?",
                color = Color(0xFF318BD3)
            )
            ))
    val onEvent: (PhraseUiEvent) -> Unit = {}

    AreaStudyCards(
      state = state,
      onEvent = onEvent
    )
}

@Composable
fun AreaStudyCards(
    modifier: Modifier = Modifier,
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit
){

        val stateCard = rememberSwipeableCardsState(
            initialCardIndex = state.curentCardPhrase,
            itemCount = { state.phrases.size })


        LaunchedEffect(state.curentCardPhrase) {
            if (state.curentCardPhrase >= state.phrases.size) {
                stateCard.setCurrentIndex(0)
                onEvent(PhraseUiEvent.UploadCurrentIndexCard(0, true))
            }
        }

        Column(
            modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Row(modifier.weight(0.10f)) {
                Text(text = "Current Card: ${state.curentCardPhrase + 1}")
                Text(text = "Total Cards: ${state.phrases.size}")
            }

            Box(
                modifier.weight(0.80f).fillMaxWidth().padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                LazySwipeableCards<PhraseUi>(
                    modifier = Modifier.fillMaxWidth().padding(end = 20.dp),
                    state = stateCard,
                    onSwipe = { phrase, direction ->
                        when (direction) {
                            SwipeableCardDirection.Right -> {
                                onEvent(PhraseUiEvent.UploadCurrentIndexCard(stateCard.currentCardIndex))
                            }

                            SwipeableCardDirection.Left -> { /* Handle left swipe */
                                onEvent(PhraseUiEvent.UploadCurrentIndexCard(stateCard.currentCardIndex))
                            }
                        }
                    }
                ) {
                    items(state.phrases) { phrase, index, offset ->
                        Box(modifier.fillMaxSize()) {
                            AdaptiveLanguageCard(
                                modifier = modifier.fillMaxSize(),
                                phrase = phrase,
                                isLandscape = isLandscape(),
                                onEdit = {
                                   onEvent(PhraseUiEvent.ShowModal(BodyModalEnum.BODY_UPDATE_PHRASE, phrase))
                                }
                            )
                        }
                    }
                }

            }
        }

}
