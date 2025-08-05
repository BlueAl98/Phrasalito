package com.nayibit.phrasalito_presentation.phraseScreen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PhraseScreen(
    modifier: Modifier = Modifier,
    viewModel: PhraseViewModel = hiltViewModel()
    ) {
    Text(text = "Phrase Screen")
}