package com.nayibit.feature_phrases.domain.model

data class Phrase(
    val id: Int = 0,
    val targetLanguage: String,
    val translation: String? = null,
    val deckId: Int,
    val example: String? = null
    )
