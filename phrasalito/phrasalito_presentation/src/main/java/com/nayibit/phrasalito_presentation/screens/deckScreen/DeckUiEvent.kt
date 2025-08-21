package com.nayibit.phrasalito_presentation.screens.deckScreen

sealed class DeckUiEvent {
    data class ShowToast(val message: String) : DeckUiEvent()
    object ShowModal: DeckUiEvent()
    object DismissModal : DeckUiEvent()
    object InsertDeck : DeckUiEvent()
    data class OpenPrompt(val url: String = "", val prompt: String = ""): DeckUiEvent()
    data class Navigation(val id: Int) : DeckUiEvent()
    data class UpdateTextFirstPhrase(val text: String) : DeckUiEvent()
}