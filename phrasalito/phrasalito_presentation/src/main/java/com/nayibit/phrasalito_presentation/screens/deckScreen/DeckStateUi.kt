package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.phrasalito_domain.model.Deck

data class DeckStateUi (
    val isLoading: Boolean = false,
    val successInsertedDeck: Deck? = null,
    val decks: List<Deck> = emptyList(),
    val errorMessage: String? = null,
    val showModal: Boolean = false,
    val nameDeck: String = "",
    val maxDeck: String = "",
    val isLoadingButton: Boolean = false
)