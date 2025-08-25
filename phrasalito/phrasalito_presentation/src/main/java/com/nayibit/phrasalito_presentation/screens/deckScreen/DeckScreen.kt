package com.nayibit.phrasalito_presentation.screens.deckScreen

import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.BaseDialog
import com.nayibit.phrasalito_presentation.composables.ButtonBase
import com.nayibit.phrasalito_presentation.composables.CardDeck
import com.nayibit.phrasalito_presentation.composables.LoadingScreen
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import kotlinx.coroutines.flow.Flow


@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    state: DeckStateUi,
    eventFlow: Flow<DeckUiEvent>,
    onEvent: (DeckUiEvent) -> Unit,
    navigation: (id: Int) -> Unit
    ) {

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is DeckUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is DeckUiEvent.Navigation -> {
                     navigation(event.id)
                }
                is DeckUiEvent.OpenPrompt -> {
                    val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clip = ClipData.newPlainText("Prompt", event.prompt)
                    clipboard.setPrimaryClip(clip)

                    Toast.makeText(context, "Prompt copied! Paste it in ChatGPT", Toast.LENGTH_SHORT).show()

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
            Box(modifier.fillMaxSize().padding(padding)) {
                when {
                    state.isLoading -> {
                        LoadingScreen()
                    }
                    state.decks.isNotEmpty() -> {
                        LazyColumn(modifier = modifier.fillMaxSize().testTag("deck_list")) {
                            items(state.decks, key = { it.id }) { phrase ->
                                Row {
                                    CardDeck(
                                        modifier = modifier.testTag("deck_item_${phrase.id}"),
                                        title = phrase.name,
                                        maxCards = phrase.maxCards,
                                        onClick = {
                                           onEvent(DeckUiEvent.Navigation(phrase.id))
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
                         TextFieldBase(
                           value = state.nameDeck,
                           onValueChange = { onEvent(DeckUiEvent.UpdateTextFirstPhrase(it))},
                           label  = stringResource(R.string.label_learn_phrase))

                        ButtonBase(
                            text = stringResource(id = R.string.btn_save),
                            onClick = { if (state.nameDeck.isNotEmpty()) onEvent(DeckUiEvent.InsertDeck) else onEvent(DeckUiEvent.ShowToast("Campo vacio")) },
                            loading = state.isLoadingButton
                        )
                        ButtonBase(
                            text = stringResource(id = R.string.btn_cancel),
                            onClick = { onEvent(DeckUiEvent.DismissModal) },
                            enabled = !state.isLoadingButton
                        )


                    }

                }


        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(DeckUiEvent.ShowModal)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )




}