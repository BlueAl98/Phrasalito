package com.nayibit.phrasalito_presentation.screens.phraseScreen

sealed class PhraseUiEvent {
    data class ShowToast(val message: String) : PhraseUiEvent()
    object ShowModal: PhraseUiEvent()
    object DismissModal : PhraseUiEvent()
    object InsertDeck : PhraseUiEvent()
}