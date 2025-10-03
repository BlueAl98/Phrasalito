package com.nayibit.phrasalito_presentation.screens.startScreen

sealed class StartUiEvent {
    object Navigate: StartUiEvent()
    object InsertSkipTutorial: StartUiEvent()
    data class ShowToast(val message: String) : StartUiEvent()
    data class SetHasPermission(val hasPermission: Boolean) : StartUiEvent()
    object NextPage: StartUiEvent()
   // data class ShowSnackbar(val message: String): StartUiEvent()
}