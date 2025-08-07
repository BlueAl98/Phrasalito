package com.nayibit.phrasalito_domain.model

data class Phrase(
    val id: Int,
    val targetLanguage: String,
    val translation: String
)
