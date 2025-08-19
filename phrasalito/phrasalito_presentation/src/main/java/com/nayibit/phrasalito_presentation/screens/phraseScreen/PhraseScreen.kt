package com.nayibit.phrasalito_presentation.screens.phraseScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.ActionIcon
import com.nayibit.phrasalito_presentation.composables.BaseDialog
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.FlipCard
import com.nayibit.phrasalito_presentation.composables.SwipeDirection
import com.nayibit.phrasalito_presentation.composables.SwipeableItemWithActions
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
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
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
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
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(state.phrases, key = { it.id }) { phrase ->
                                SwipeableItemWithActions(
                                    direction = SwipeDirection.EndToStart,
                                    isRevealed = phrase.isOptionsRevealed,
                                    onExpanded = { onEvent(PhraseUiEvent.ExpandItem(phrase.id))},
                                    onCollapsed = { onEvent(PhraseUiEvent.CollapsedItem(phrase.id))},
                                    actions = {
                                        ActionIcon(
                                            modifier = modifier.fillMaxHeight(),
                                            onClick = { onEvent(PhraseUiEvent.ShowModal(BodyModalEnum.BODY_DELETE_PHRASE,phrase)) },
                                            icon = Icons.Default.Delete)
                                        ActionIcon(
                                            modifier = modifier.fillMaxHeight(),
                                            onClick = { onEvent(PhraseUiEvent.ShowModal(BodyModalEnum.BODY_UPDATE_PHRASE,phrase))},
                                            icon = Icons.Default.Edit
                                            )
                                    }
                                ){
                                    FlipCard(
                                        phrase = phrase.targetLanguage,
                                        translation = phrase.translation,
                                    )
                                }

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

    ButtonBase(
        text = stringResource(id = R.string.btn_save),
        onClick = { if (state.firstPhrase.isNotEmpty() && state.translation.isNotEmpty()) onEvent(PhraseUiEvent.InsertPhrase) else onEvent(PhraseUiEvent.ShowToast("Campo vacio")) },
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

    ButtonBase(
        text = stringResource(id = R.string.btn_update),
        onClick = { if (state.firstPhrase.isNotEmpty() && state.translation.isNotEmpty()) onEvent(PhraseUiEvent.UpdatePhrase(state.phraseToUpdate!!)) else onEvent(PhraseUiEvent.ShowToast("Campo vacio")) },
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
