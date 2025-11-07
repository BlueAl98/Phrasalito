package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.common.util.UiText
import com.nayibit.phrasalito_presentation.model.Language

sealed class DeckUiEvent {
    data class ShowToast(val message: UiText) : DeckUiEvent()
    data class ShowModal(val type: BodyDeckModalEnum,val deck: DeckUI = DeckUI(name = "", numCards = 0)) : DeckUiEvent()
    object DismissModal : DeckUiEvent()
    object InsertDeck : DeckUiEvent()
    data class OpenPrompt(val url: String = "", val prompt: String = "") : DeckUiEvent()
    data class NavigationToPhrases(val id: Int, val lngCode: String?) : DeckUiEvent()
    data class UpdateTextFieldInsert(val text: String) : DeckUiEvent()
    data class UpdateTextFieldUpdate(val text: String) : DeckUiEvent()
    data class DeleteDeck(val id: Int) : DeckUiEvent()
    object  UpdateDeck: DeckUiEvent()
    data class UpdateDeckList(val idDeck: Int, val isSwiped: Boolean): DeckUiEvent()
    object ResetAllSwiped : DeckUiEvent()
    data class ShowSnackbar(val message: UiText) : DeckUiEvent()
    data class OnLanguageSelected(val language: Language): DeckUiEvent()
    data class UpdateNotificationState(val isNotified: Boolean): DeckUiEvent()
    object TutorialFinish : DeckUiEvent()
    object onNextStep: DeckUiEvent()
}


