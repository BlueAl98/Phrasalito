package com.ie.feature_startscreen.presentation.startScreen

import com.nayibit.utils.SelectScreen

sealed class StartUiEvent {
    data class Navigate(val screen: SelectScreen): StartUiEvent()
    object InsertSkipTutorial: StartUiEvent()
    data class ShowToast(val message: String) : StartUiEvent()
    object NextPage: StartUiEvent()

}