package com.nayibit.phrasalito_presentation.screens.exerciseScreen

sealed class ExerciseUiEvent  {
    data class ShowToast(val message: String) : ExerciseUiEvent()
    object NavigateNext : ExerciseUiEvent()
    object OnStartClicked : ExerciseUiEvent()
    data class OnInputChanged(val input: String, val currentIndex: Int) : ExerciseUiEvent()
    object OnNextPhrase : ExerciseUiEvent()
    data class UpdateExpandedState(val expanded: Boolean) : ExerciseUiEvent()
    data class OnSpeakPhrase(val text: String) : ExerciseUiEvent()

}