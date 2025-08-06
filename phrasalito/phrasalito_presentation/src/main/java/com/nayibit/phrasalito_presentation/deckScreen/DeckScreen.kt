package com.nayibit.phrasalito_presentation.deckScreen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nayibit.phrasalito_domain.model.Deck


@Composable
fun DeckScreen(
    modifier: Modifier = Modifier,
    viewModel: DeckViewModel = hiltViewModel()
    ) {

 /*   val state by viewModel.state.collectAsStateWithLifecycle()
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


    Column (modifier = modifier.fillMaxSize()) {
        Button(onClick = {viewModel.insertDeck(Deck(name = "Test", maxCards = 10))}) {
            Text("Insert")
        }



    }
*/


}