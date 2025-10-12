package com.nayibit.phrasalito_presentation.screens.deckScreen

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.nayibit.common.util.UiText
import com.nayibit.common.util.asString
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.BaseDialog
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.LoadingScreen
import com.nayibit.phrasalito_presentation.composables.SwipeableDeckItem
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch


@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    state: DeckStateUi,
    eventFlow: Flow<DeckUiEvent>,
    onEvent: (DeckUiEvent) -> Unit,
    navigationToPhrases: (id: Int) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is DeckUiEvent.ShowToast -> {
                    val message = event.message.asString(context)
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                }

                is DeckUiEvent.NavigationToPhrases -> {
                    navigationToPhrases(event.id)
                }

                is DeckUiEvent.OpenPrompt -> {
                    val clipboard =
                        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Prompt", event.prompt)
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(
                        context,
                        "Prompt copied! Paste it in ChatGPT",
                        Toast.LENGTH_SHORT
                    ).show()

                    val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                    try {
                        context.startActivity(intent)
                    } catch (_: ActivityNotFoundException) {
                        Toast.makeText(context, "No browser found", Toast.LENGTH_SHORT).show()
                    }
                }

                else -> {}
            }
        }
    }

    Scaffold(
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

                    state.decks.isNotEmpty() -> {

                        LazyColumn(
                            modifier = modifier
                                .fillMaxSize()
                                .testTag("deck_list")
                        ) {
                            items(state.decks, key = { it.id }) { deck ->

                                var visible by remember { mutableStateOf(true) }

                                AnimatedVisibility(
                                    visible = visible,
                                    enter = fadeIn(animationSpec = tween(300)) + expandVertically(),
                                    exit = fadeOut(animationSpec = tween(300)) + shrinkVertically(),
                                ) {
                                    SwipeableDeckItem(
                                        deck = deck,
                                        onEdit = { onEvent(DeckUiEvent.ShowModal(BodyDeckModalEnum.BODY_UPDATE_DECK, it)) },
                                        onDelete = {
                                         onEvent(DeckUiEvent.ShowModal(BodyDeckModalEnum.BODY_DELETE_DECK, it))
                                        },
                                        onClick = { onEvent(DeckUiEvent.NavigationToPhrases(it.id))},
                                        isSwiped = deck.isSwiped,
                                        onSwipe = {isSwiped ->
                                            // 👇 when one deck is swiped, update the list so only that one is true
                                            val updatedDecks = state.decks.map {
                                                if (it.id == deck.id) it.copy(isSwiped = isSwiped)
                                                else it.copy(isSwiped = false)
                                            }
                                            onEvent(DeckUiEvent.UpdateDeckList(updatedDecks))
                                        }
                                    )
                                }
                            }
                        }
                    }
                }



                BaseDialog(
                    showDialog = state.showModal,
                    offsideDismiss = false
                ) {
                    when (state.bodyModal) {
                        BodyDeckModalEnum.BODY_INSERT_DECK -> BodyModalInsertDeck(
                            state = state,
                            onEvent = onEvent
                        )

                        BodyDeckModalEnum.BODY_UPDATE_DECK -> BodyModalUpdateDeck(
                            state = state,
                            onEvent = onEvent)

                        BodyDeckModalEnum.BODY_DELETE_DECK -> BodyModalDeleteDeck(
                            state = state,
                            onEvent = onEvent
                        )
                    }

                }

            }


        }, floatingActionButton = {
            FloatingActionButton(
                containerColor = primaryGradientEnd,
                onClick = {
                    onEvent(DeckUiEvent.ShowModal(BodyDeckModalEnum.BODY_INSERT_DECK))
                }) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    )

}


@Composable
fun BodyModalInsertDeck(
    state: DeckStateUi,
    onEvent: (DeckUiEvent) -> Unit
) {

    TextFieldBase(
        value = state.nameDeck,
        onValueChange = { onEvent(DeckUiEvent.UpdateTextFieldInsert(it)) },
        label = stringResource(R.string.label_learn_phrase)
    )

    ButtonBase(
        text = stringResource(id = R.string.btn_save),
        onClick = {
            if (state.nameDeck.isNotEmpty()) onEvent(DeckUiEvent.InsertDeck) else onEvent(
                DeckUiEvent.ShowToast(UiText.StringResource(R.string.label_emty_fields))
            )
        },
        loading = state.isLoadingButton
    )
    ButtonBase(
        text = stringResource(id = R.string.btn_cancel),
        onClick = { onEvent(DeckUiEvent.DismissModal) },
        enabled = !state.isLoadingButton
    )

}

@Composable
fun BodyModalUpdateDeck(
    state: DeckStateUi,
    onEvent: (DeckUiEvent) -> Unit,
    modifier: Modifier = Modifier,
) {

    Text(text = stringResource(R.string.title_update_deck))

    TextFieldBase(
        value = state.nameDeck,
        onValueChange = { onEvent(DeckUiEvent.UpdateTextFieldUpdate(it))},
        label = stringResource(R.string.label_deck)
    )

    ButtonBase(
        text = stringResource(R.string.btn_update),
        onClick = { onEvent(DeckUiEvent.UpdateDeck(state.currentDeck.id, state.nameDeck))},
        loading = state.isLoadingButton
    )
    ButtonBase(
        text = stringResource(R.string.btn_cancel),
        onClick = { onEvent(DeckUiEvent.DismissModal) },
        enabled = !state.isLoadingButton
    )

}

@Composable
fun BodyModalDeleteDeck(
    state: DeckStateUi,
    onEvent: (DeckUiEvent) -> Unit
) {
    Text(text = stringResource(R.string.title_delete_deck))

    ButtonBase(
        text = stringResource(R.string.btn_delete),
        onClick = { onEvent(DeckUiEvent.DeleteDeck(state.currentDeck.id))},
        loading = state.isLoadingButton
    )
    ButtonBase(
        text = stringResource(R.string.btn_cancel),
        onClick = { onEvent(DeckUiEvent.DismissModal) },
        enabled = !state.isLoadingButton
    )
}

