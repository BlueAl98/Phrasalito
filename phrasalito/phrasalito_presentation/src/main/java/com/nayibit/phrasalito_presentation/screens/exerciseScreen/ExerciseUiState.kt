package com.nayibit.phrasalito_presentation.screens.exerciseScreen

data class ExerciseUiState (
    val isLoading: Boolean = false,
    val title: String = "Exercise Time!",
    val phrases: List<String> = listOf("dont _ me _", "pick _ on me", "carry _ with work"),
    val currentIndex: Int = 1,
    val totalItems: Int = 3,
    val userInput: String = ""
)