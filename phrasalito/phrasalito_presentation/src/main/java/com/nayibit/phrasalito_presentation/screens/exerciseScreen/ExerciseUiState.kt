package com.nayibit.phrasalito_presentation.screens.exerciseScreen


data class ExerciseUiState (
    val isLoading: Boolean = false,
    val title: String = "Exercise Time!",
    val phrases: List<ExercisePhrases> = emptyList(),
    val currentIndex: Int = 0,
    val totalItems: Int = 10,
    val inputAnswer : String = ""
)

data class ExercisePhrases(
    val id: Int,
    val targetLanguage: String,
    val translation: String,
    val example: String = ""
)