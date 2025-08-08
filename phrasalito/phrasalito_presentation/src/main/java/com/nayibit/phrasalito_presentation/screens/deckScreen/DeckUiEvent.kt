package com.nayibit.phrasalito_presentation.screens.deckScreen

sealed class DeckUiEvent {
    data class ShowToast(val message: String) : DeckUiEvent()
    object ShowModal: DeckUiEvent()
    object DismissModal : DeckUiEvent()
    object TriggerModal : DeckUiEvent()
}