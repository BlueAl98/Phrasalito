package com.nayibit.phrasalito_presentation.deckScreen

import com.nayibit.phrasalito_domain.model.Deck

data class DeckStateUi (
    val isLoading: Boolean = false,
    val successInsertedDeck: Deck? = null,
    val errorMessage: String? = null
)