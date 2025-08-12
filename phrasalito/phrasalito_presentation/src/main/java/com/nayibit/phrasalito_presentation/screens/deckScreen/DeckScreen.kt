package com.nayibit.phrasalito_presentation.screens.deckScreen

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.BaseDialog
import com.nayibit.phrasalito_presentation.composables.CardDeck
import com.nayibit.phrasalito_presentation.composables.LoadingScreen
import com.nayibit.phrasalito_presentation.composables.TextFieldBase
import kotlinx.coroutines.flow.Flow


@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    state: DeckStateUi,
    eventFlow: Flow<DeckUiEvent>,
    onEvent: (DeckUiEvent) -> Unit
    ) {

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is DeckUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is DeckUiEvent.ShowModal -> {
                     onEvent(DeckUiEvent.ShowModal)
                }
                is DeckUiEvent.DismissModal -> {
                    onEvent(DeckUiEvent.DismissModal)
                }
                is DeckUiEvent.TriggerModal -> {
                    onEvent(DeckUiEvent.TriggerModal)
                }
                is DeckUiEvent.UpdateTextFirstPhrase -> {
                    onEvent(DeckUiEvent.UpdateTextFirstPhrase(event.text))
                }

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
                                        title = phrase.name
                                    )
                                }

                            }
                        }
                    }

                }


                    BaseDialog(
                        showDialog = state.showModal,
                        onDismissRequest = { onEvent(DeckUiEvent.DismissModal) }
                    ) {
                        TextFieldBase(
                            value = state.textFirstPhrase,
                            onValueChange = { onEvent(DeckUiEvent.UpdateTextFirstPhrase(it))},
                            label  = stringResource(R.string.label_first_phrase)
                        )
                    }

                }


        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(DeckUiEvent.TriggerModal)
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )




}