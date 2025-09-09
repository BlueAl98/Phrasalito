package com.nayibit.phrasalito_presentation.screens.exerciseScreen


data class ExerciseUiState (
    val isLoading: Boolean = false,
    val title: String = "Exercise Time!",
    val phrases: List<ExercisePhrases> = emptyList(),
    val currentIndex: Int = 0,
    val totalItems: Int = 10,
    val inputAnswer : String = "",
    val popOverState: Boolean = false,
    val ttsState : Boolean = false,
    val testCompleted : Boolean = false,
    val showDialog : Boolean = false
)

data class ExercisePhrases(
    val id: Int,
    val targetLanguage: String,
    val translation: String,
    val example: String = "",
    val correctAnswer: String = "",
    val phraseState: PhraseState = PhraseState.NOT_STARTED
)

enum class PhraseState(val value: Int){
    NOT_STARTED(0),
    ERROR_ANSWER(1),
    COMPLETED(2)
}