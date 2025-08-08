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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_presentation.composables.CardDeck
import com.nayibit.phrasalito_presentation.composables.LoadingScreen


@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    viewModel: DeckViewModel = hiltViewModel()
    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current


    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is DeckUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
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
                        LazyColumn(modifier = modifier.fillMaxSize()) {
                            items(state.decks, key = { it.id }) { phrase ->
                                Row {
                                    CardDeck(
                                        title = phrase.name
                                    )
                                }

                            }
                        }
                    }

                }
            }

        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.insertDeck(Deck(name = "Test", maxCards = 10))
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    )




}