package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.common.util.UiText
import com.nayibit.phrasalito_domain.model.Deck

sealed class DeckUiEvent {
    data class ShowToast(val message: UiText) : DeckUiEvent()
    data class ShowModal(val type: BodyDeckModalEnum,val deck: Deck = Deck(name = "", maxCards = 0)) : DeckUiEvent()
    object DismissModal : DeckUiEvent()
    object InsertDeck : DeckUiEvent()
    data class OpenPrompt(val url: String = "", val prompt: String = "") : DeckUiEvent()
    data class NavigationToPhrases(val id: Int) : DeckUiEvent()
    data class UpdateTextFieldInsert(val text: String) : DeckUiEvent()
    data class UpdateTextFieldUpdate(val text: String) : DeckUiEvent()
    data class DeleteDeck(val id: Int) : DeckUiEvent()
    data class UpdateDeck(val id: Int, val nameDeck: String = ""): DeckUiEvent()
}


