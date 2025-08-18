package com.nayibit.phrasalito_presentation.screens.phraseScreen

import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent

sealed class PhraseUiEvent {
    data class ShowToast(val message: String) : PhraseUiEvent()
    object ShowModal: PhraseUiEvent()
    object DismissModal : PhraseUiEvent()
    object InsertPhrase : PhraseUiEvent()
    data class UpdateTextFirstPhrase(val text: String) : PhraseUiEvent()
    data class UpdateTextTraslation(val text: String) : PhraseUiEvent()
    data class ExpandItem(val id: Int) : PhraseUiEvent()
    data class CollapsedItem(val id: Int) : PhraseUiEvent()

}