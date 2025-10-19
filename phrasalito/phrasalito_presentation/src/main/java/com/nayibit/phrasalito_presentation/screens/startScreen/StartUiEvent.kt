package com.nayibit.phrasalito_presentation.screens.startScreen

sealed class StartUiEvent {
    object Navigate: StartUiEvent()
    object InsertSkipTutorial: StartUiEvent()
    data class ShowToast(val message: String) : StartUiEvent()
    object NextPage: StartUiEvent()

}