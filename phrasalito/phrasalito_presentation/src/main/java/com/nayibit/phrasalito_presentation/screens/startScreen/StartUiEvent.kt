package com.nayibit.phrasalito_presentation.screens.startScreen

import com.nayibit.phrasalito_presentation.model.Language

sealed class StartUiEvent {
    object Navigate: StartUiEvent()
    object InsertSkipTutorial: StartUiEvent()
    data class ShowToast(val message: String) : StartUiEvent()
    object NextPage: StartUiEvent()
    data class SetLanguage(val language: Language): StartUiEvent()
    data class SetScrollPosition(val index: Int, val offset: Int): StartUiEvent()
    object InsertLanguage : StartUiEvent()
}