package com.nayibit.phrasalito_presentation.screens.phraseScreen

import com.nayibit.phrasalito_domain.model.Phrase

data class PhraseState(
    val isLoading: Boolean = false,
    val phrases: List<Phrase> = emptyList()
)
