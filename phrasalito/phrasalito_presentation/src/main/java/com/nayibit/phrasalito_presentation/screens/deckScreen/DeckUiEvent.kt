package com.nayibit.phrasalito_presentation.screens.deckScreen

sealed class DeckUiEvent {
    data class ShowToast(val message: String) : DeckUiEvent()
}