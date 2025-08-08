package com.nayibit.phrasalito_presentation.screens.phraseScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment

@Composable
fun PhraseScreen(
    modifier: Modifier = Modifier,
    viewModel: PhraseViewModel = hiltViewModel()
    ) {
    val state by viewModel.state.collectAsStateWithLifecycle()


    when{
        state.isLoading -> {
            Box(modifier.fillMaxSize()){
            CircularProgressIndicator(modifier.align(Alignment.Center))
        }
        }
        state.phrases.isNotEmpty() -> {
            LazyColumn(modifier = modifier.fillMaxSize()) {
               items(state.phrases) { phrase ->
                   Row {
                       Text(text = phrase.targetLanguage)
                       Text(text = phrase.translation)
                   }

               }
            }
        }
        else ->  Text(text = "No phrases found")
    }


}