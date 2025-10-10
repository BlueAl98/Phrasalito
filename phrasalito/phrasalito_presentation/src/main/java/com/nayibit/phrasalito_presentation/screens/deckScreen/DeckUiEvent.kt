package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.common.util.UiText

sealed class DeckUiEvent {
    data class ShowToast(val message: UiText) : DeckUiEvent()
    data class ShowModal(val type: BodyDeckModalEnum, val id: Int = 0) : DeckUiEvent()
    object DismissModal : DeckUiEvent()
    object InsertDeck : DeckUiEvent()
    data class OpenPrompt(val url: String = "", val prompt: String = "") : DeckUiEvent()
    data class NavigationToPhrases(val id: Int) : DeckUiEvent()
    data class UpdateTextFirstPhrase(val text: String) : DeckUiEvent()
}