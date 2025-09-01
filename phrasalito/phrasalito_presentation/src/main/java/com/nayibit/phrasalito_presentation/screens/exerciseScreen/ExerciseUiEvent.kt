package com.nayibit.phrasalito_presentation.screens.exerciseScreen

sealed class ExerciseUiEvent  {
    data class ShowToast(val message: String) : ExerciseUiEvent()
    object NavigateNext : ExerciseUiEvent()
    object OnStartClicked : ExerciseUiEvent()
}