package com.nayibit.phrasalito_presentation.screens.phraseScreen

import com.nayibit.common.util.UiText

sealed class PhraseUiEvent {
    data class ShowToast(val message: UiText) : PhraseUiEvent()
    data class ShowModal(val type: BodyModalEnum, val phraseUi: PhraseUi? = null): PhraseUiEvent()
    object DismissModal : PhraseUiEvent()
    object InsertPhrase : PhraseUiEvent()
    data class UpdateTextFirstPhrase(val text: String) : PhraseUiEvent()
    data class UpdateTextTraslation(val text: String) : PhraseUiEvent()
    data class UpdateTextExample(val text: String) : PhraseUiEvent()
    data class ExpandItem(val id: Int) : PhraseUiEvent()
    data class CollapsedItem(val id: Int) : PhraseUiEvent()
    data class DeletePhrase(val id: Int) : PhraseUiEvent()
    data class UpdatePhrase(val phraseUi: PhraseUi) : PhraseUiEvent()
    data class UploadCurrentIndexCard(val index: Int , val reset: Boolean = false ) : PhraseUiEvent()
}