package com.nayibit.phrasalito_presentation.screens.exerciseScreen

sealed class ExerciseUiEvent  {
    data class ShowSnackBar(val message: String) : ExerciseUiEvent()
    object NavigateNext : ExerciseUiEvent()
    object OnStartClicked : ExerciseUiEvent()
    data class OnInputChanged(val input: String, val currentIndex: Int) : ExerciseUiEvent()
    data class OnNextPhrase(val currentIndex: Int) : ExerciseUiEvent()
    data class UpdateExpandedState(val expanded: Boolean) : ExerciseUiEvent()
    data class OnSpeakPhrase(val text: String) : ExerciseUiEvent()
    data class ShowDialog(val show: Boolean, val type: BodyModalExercise) : ExerciseUiEvent()
    data class ShowAllInfo(val currentIndex: Int) : ExerciseUiEvent()
}