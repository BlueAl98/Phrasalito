package com.ie.feature_startscreen.presentation.startScreen

sealed class StartUiEvent {
    object Navigate: StartUiEvent()
    object InsertSkipTutorial: StartUiEvent()
    data class ShowToast(val message: String) : StartUiEvent()
    object NextPage: StartUiEvent()

}