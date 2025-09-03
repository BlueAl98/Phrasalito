package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent

sealed class ExerciseUiEvent  {
    data class ShowToast(val message: String) : ExerciseUiEvent()
    object NavigateNext : ExerciseUiEvent()
    object OnStartClicked : ExerciseUiEvent()
    data class OnPhraseSelected(val phrase: String) : ExerciseUiEvent()
    data class OnInputChanged(val input: String) : ExerciseUiEvent()
    data class OnCheckClicked(val currentIndex: Int) : ExerciseUiEvent()
    object OnNextClicked : ExerciseUiEvent()
    data class UpdateExpandedState(val expanded: Boolean) : ExerciseUiEvent()


}