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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.nayibit.common.util.Constants.OP_TARGET_LANGUAGE
import com.nayibit.common.util.UiText
import com.nayibit.common.util.asString
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.AdaptiveLanguageCard
import com.nayibit.phrasalito_presentation.composables.BaseDialog
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.DynamicIconSection
import com.nayibit.phrasalito_presentation.composables.LoadingScreen
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import com.nayibit.phrasalito_presentation.composables.isLandscape
import com.nayibit.phrasalito_presentation.model.IconItem
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart
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
) {
    val context = LocalContext.current
    val snackbarHostState = remember { androidx.compose.material3.SnackbarHostState() }


    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is PhraseUiEvent.ShowToast -> {
                    val message = event.message.asString(context)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                is PhraseUiEvent.Navigation -> {
                    navigation(state.idDeck)
                }
                is PhraseUiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(event.message.asString(context))
                }

                else -> {}
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        content = { padding ->
            Box(
                modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                when {
                    state.isLoading -> {
                        LoadingScreen()
                    }

                   else -> {
                        key(state.phrases) {
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
                }

                BaseDialog(
                    showDialog = state.showModal,
                    offsideDismiss = false
                ) {
                    when (state.bodyModal) {
                        BodyModalEnum.BODY_INSERT_PHRASE -> BodyModalInsertPhrase(state, onEvent)
                        BodyModalEnum.BODY_UPDATE_PHRASE -> BodyModalUpdatePhrase(state, onEvent)
                        BodyModalEnum.BODY_DELETE_PHRASE -> BodyModalDeletePhrase(state, onEvent)
                        BodyModalEnum.BODY_START_EXERCISE -> BodyModalStartExercise(onEvent)
                    }

                }

            }
        })
}


@Composable
fun BodyModalStartExercise(
    onEvent: (PhraseUiEvent) -> Unit
) {

    Text(stringResource(id = R.string.label_modal_start_exercise))

    ButtonBase(
        text = stringResource(id = R.string.btn_accept),
        onClick = { onEvent(PhraseUiEvent.Navigation) })
    ButtonBase(
        text = stringResource(id = R.string.btn_cancel),
        onClick = { onEvent(PhraseUiEvent.DismissModal) }
    )
}


@Composable
fun BodyModalInsertPhrase(
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusRequesterPhrase = remember { FocusRequester() }
    val focusRequesterTranslation = remember { FocusRequester() }
    val focusRequesterExample = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    TextFieldBase(
        value = state.firstPhrase,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextFirstPhrase(it)) },
        label = stringResource(R.string.label_phrase),
        maxChar = 30,
        textRestriction = true,
        showCharCounter = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusRequesterTranslation.requestFocus() } // 👈 pasa al siguiente
        ),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequesterPhrase)
    )

    TextFieldBase(
        value = state.translation,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextTraslation(it)) },
        label = stringResource(R.string.label_traduction_phrase),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusRequesterExample.requestFocus() }),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequesterTranslation)
    )

    TextFieldBase(
        value = state.example,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextExample(it)) },
        label = stringResource(R.string.label_example_phrase),
        textRestriction = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.clearFocus() }),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequesterExample)
    )

    if (!isLandscape()) {
        ButtonBase(
            text = stringResource(id = R.string.btn_save),
            onClick = {
                if (state.firstPhrase.isNotEmpty()) onEvent(PhraseUiEvent.InsertPhrase
                ) else onEvent(
                    PhraseUiEvent.ShowToast(
                        UiText.StringResource(R.string.label_target_language)
                    )
                )
            },
            loading = state.isLoadingButton
        )
        ButtonBase(
            text = stringResource(id = R.string.btn_cancel),
            onClick = { onEvent(PhraseUiEvent.DismissModal) },
            enabled = !state.isLoadingButton
        )
    } else {
        Row {
            ButtonBase(
                modifier = modifier.weight(0.45f),
                text = stringResource(id = R.string.btn_save),
                onClick = {
                    if (state.firstPhrase.isNotEmpty()) onEvent(PhraseUiEvent.InsertPhrase
                    ) else onEvent(
                        PhraseUiEvent.ShowToast(
                            UiText.StringResource(R.string.label_target_language)
                        )
                    )
                },
                loading = state.isLoadingButton
            )
            Spacer(modifier.weight(0.05f))
            ButtonBase(
                modifier = modifier.weight(0.45f),
                text = stringResource(id = R.string.btn_cancel),
                onClick = { onEvent(PhraseUiEvent.DismissModal) },
                enabled = !state.isLoadingButton
            )
        }
    }

}


@Composable
fun BodyModalUpdatePhrase(
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    val focusRequesterPhrase = remember { FocusRequester() }
    val focusRequesterTranslation = remember { FocusRequester() }
    val focusRequesterExample = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current


    TextFieldBase(
        value = state.firstPhrase,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextFirstPhrase(it)) },
        label = stringResource(R.string.label_phrase),
        maxChar = 30,
        showCharCounter = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusRequesterTranslation.requestFocus() } // 👈 pasa al siguiente
        ),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequesterPhrase)
    )

    TextFieldBase(
        value = state.translation,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextTraslation(it)) },
        label = stringResource(R.string.label_traduction_phrase),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusRequesterExample.requestFocus() }),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequesterTranslation)
    )

    TextFieldBase(
        value = state.example,
        onValueChange = { onEvent(PhraseUiEvent.UpdateTextExample(it)) },
        label = stringResource(R.string.label_example_phrase),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.clearFocus() }),
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequesterExample)
    )

    if (!isLandscape()) {
        ButtonBase(
            text = stringResource(id = R.string.btn_update),
            onClick = {
                if (state.firstPhrase.isNotEmpty()) onEvent(
                    PhraseUiEvent.UpdatePhrase(state.phraseToUpdate!!)
                ) else onEvent(
                    PhraseUiEvent.ShowToast(
                        UiText.StringResource(R.string.label_target_language)
                    )
                )
            },
            loading = state.isLoadingButton
        )
        ButtonBase(
            text = stringResource(id = R.string.btn_cancel),
            onClick = { onEvent(PhraseUiEvent.DismissModal) },
            enabled = !state.isLoadingButton
        )
    } else {
        Row {
            ButtonBase(
                modifier = modifier.weight(0.45f),
                text = stringResource(id = R.string.btn_update),
                onClick = {
                    if (state.firstPhrase.isNotEmpty()) onEvent(
                        PhraseUiEvent.UpdatePhrase(state.phraseToUpdate!!)
                    ) else onEvent(
                        PhraseUiEvent.ShowToast(
                            UiText.StringResource(R.string.label_target_language)
                        )
                    )
                },
                loading = state.isLoadingButton
            )

            Spacer(modifier.weight(0.05f))

            ButtonBase(
                modifier = modifier.weight(0.45f),
                text = stringResource(id = R.string.btn_cancel),
                onClick = { onEvent(PhraseUiEvent.DismissModal) },
                enabled = !state.isLoadingButton
            )
        }
    }
}

@Composable
fun BodyModalDeletePhrase(
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit
) {

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
fun AreaStudyCards(
    modifier: Modifier = Modifier,
    state: PhraseStateUi,
    onEvent: (PhraseUiEvent) -> Unit
) {

    val icons = listOf(
        IconItem(Icons.Default.AddCircle,
            stringResource(R.string.add_card_phrase), primaryGradientStart, onClick = {
            onEvent(PhraseUiEvent.ShowModal(BodyModalEnum.BODY_INSERT_PHRASE))
        }),
        IconItem(Icons.Default.Edit, stringResource(R.string.update_card_phrase), primaryGradientStart, onClick = {
            onEvent(
                PhraseUiEvent.ShowModal(
                    BodyModalEnum.BODY_UPDATE_PHRASE,
                    state.phrases[state.curentCardPhrase]
                )
            )
        }, enabled = state.phrases.isNotEmpty()),

        IconItem(Icons.Default.Delete, stringResource(R.string.delete_card_phrase), primaryGradientStart, onClick = {
            onEvent(
                PhraseUiEvent.ShowModal(
                    BodyModalEnum.BODY_DELETE_PHRASE,
                    state.phrases[state.curentCardPhrase]
                )
            )
        },enabled = state.phrases.isNotEmpty()),
        IconItem(Icons.Default.Description, stringResource(R.string.test_card_phrase), primaryGradientStart, onClick = {
            if (state.isReadyForTest)
            onEvent(
                PhraseUiEvent.ShowModal(
                    BodyModalEnum.BODY_START_EXERCISE
                )
            )
            else
                onEvent(PhraseUiEvent.ShowSnackbar(UiText.StringResource(R.string.label_dont_cards_enough)))
        })
    )

    val stateCard = rememberSwipeableCardsState(
        initialCardIndex = state.curentCardPhrase,
        itemCount = { state.phrases.size })


    LaunchedEffect(state.curentCardPhrase) {
        if (state.curentCardPhrase >= state.phrases.size) {
            stateCard.setCurrentIndex(0)
            onEvent(PhraseUiEvent.UploadCurrentIndexCard(0, true))
        }
    }


    if (!isLandscape()) {
        Column(
            modifier
                .fillMaxSize()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier
                    .weight(0.10f)
                    .padding(5.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = modifier.padding(5.dp),
                    text = "Cards: ${
                       when{
                           state.phrases.isNotEmpty() && state.curentCardPhrase < state.phrases.size -> state.curentCardPhrase + 1
                           else -> state.curentCardPhrase
                       }
                    
                    } /  ${state.phrases.size}",
                    style = MaterialTheme.typography.titleLarge
                )

            }

            DynamicIconSection(items = icons)


            Spacer(modifier.weight(0.05f))

            Box(
                modifier
                    .weight(0.80f)
                    .fillMaxWidth()
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                LazySwipeableCards<PhraseUi>(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 20.dp),
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
                                onEvent = { select ->
                                    val textToSpeak = when (select) {
                                        OP_TARGET_LANGUAGE -> phrase.targetLanguage.takeIf { it.isNotEmpty() }
                                        else -> phrase.example?.takeIf { it.isNotEmpty() }
                                    }

                                    textToSpeak?.let {
                                        onEvent(PhraseUiEvent.SpeakText(it))
                                    }
                              },
                                isTTsReady = state.isTTsReady,
                                isLanguageVoiceSet = state.lngCode != "",
                                isTtsSpeaking = state.isTtsSpeaking
                            )
                        }
                    }
                }

            }
        }
    } else {

        Row(
            modifier
                .fillMaxSize()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center

        ) {

            Box(
                modifier
                    .weight(0.30f)
                    .fillMaxHeight(0.8f),
                contentAlignment = Alignment.Center
            ) {

                Card(
                    modifier = modifier.fillMaxSize(),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Column(
                        modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            modifier = modifier.padding(5.dp),
                            text = "Cards",
                            style = MaterialTheme.typography.titleLarge,
                        )
                        Text(
                            modifier = modifier.padding(5.dp),
                            text = "${  when{
                                state.phrases.isNotEmpty() && state.curentCardPhrase < state.phrases.size -> state.curentCardPhrase + 1
                                else -> state.curentCardPhrase
                            }
                            } /  ${state.phrases.size}",
                            style = MaterialTheme.typography.titleLarge,
                        )

                        DynamicIconSection(items = icons, columns = 2)

                    }

                }
            }

            Box(
                modifier.weight(0.65f),
                contentAlignment = Alignment.Center
            ) {

                LazySwipeableCards<PhraseUi>(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .fillMaxHeight(0.9f)
                        .padding(end = 20.dp),
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
                                onEvent = { select ->
                                    val textToSpeak = when (select) {
                                        OP_TARGET_LANGUAGE -> phrase.targetLanguage.takeIf { it.isNotEmpty() }
                                        else -> phrase.example?.takeIf { it.isNotEmpty() }
                                    }

                                    textToSpeak?.let {
                                        onEvent(PhraseUiEvent.SpeakText(it))
                                    }
                                },
                                isTTsReady = state.isTTsReady,
                                isLanguageVoiceSet = state.lngCode != "",
                                isTtsSpeaking = state.isTtsSpeaking
                            )
                        }
                    }
                }

            }
        }

    }
}
