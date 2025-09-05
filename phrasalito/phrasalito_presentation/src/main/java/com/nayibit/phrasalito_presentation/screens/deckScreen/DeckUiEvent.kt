package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.common.util.UiText

sealed class DeckUiEvent {
    data class ShowToast(val message: UiText) : DeckUiEvent()
    object ShowModal : DeckUiEvent()
    object DismissModal : DeckUiEvent()
    object InsertDeck : DeckUiEvent()
    data class OpenPrompt(val url: String = "", val prompt: String = "") : DeckUiEvent()
    data class NavigationToPhrases(val id: Int) : DeckUiEvent()
    data class NavigationToExercise(val id: Int) : DeckUiEvent()
    data class UpdateTextFirstPhrase(val text: String) : DeckUiEvent()
}