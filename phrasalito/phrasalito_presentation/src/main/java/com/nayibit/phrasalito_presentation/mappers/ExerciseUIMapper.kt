package com.nayibit.phrasalito_presentation.mappers

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExercisePhrases
import com.nayibit.phrasalito_presentation.utils.exercisePhrase

fun Phrase.toExerciseUI(): ExercisePhrases {
    return ExercisePhrases(
        id = this.id,
        targetLanguage = this.targetLanguage,
        translation = this.translation ?: "",
        example = exercisePhrase(this.targetLanguage,this.example ?: ""),
        correctAnswer = this.example ?: ""
    )

}