package com.nayibit.phrasalito_presentation.deckScreen

sealed class DeckUiEvent {
    data class ShowToast(val message: String) : DeckUiEvent()
}