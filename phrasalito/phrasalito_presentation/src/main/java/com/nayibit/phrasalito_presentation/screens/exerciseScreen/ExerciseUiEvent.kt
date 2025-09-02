package com.nayibit.phrasalito_presentation.screens.exerciseScreen

sealed class ExerciseUiEvent  {
    data class ShowToast(val message: String) : ExerciseUiEvent()
    object NavigateNext : ExerciseUiEvent()
    object OnStartClicked : ExerciseUiEvent()
    data class OnPhraseSelected(val phrase: String) : ExerciseUiEvent()
    data class OnInputChanged(val input: String) : ExerciseUiEvent()
    object OnCheckClicked : ExerciseUiEvent()
    object OnNextClicked : ExerciseUiEvent()

}