package com.nayibit.phrasalito_domain.model

data class Phrase(
    val id: Int = 0,
    val targetLanguage: String,
    val translation: String,
    val deckId: Int
)
