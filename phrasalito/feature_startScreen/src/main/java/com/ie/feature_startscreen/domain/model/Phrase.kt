package com.ie.feature_startscreen.domain.model

data class Phrase(
    val id: Int = 0,
    val targetLanguage: String,
    val translation: String? = null,
    val deckId: Int,
    val isNotified : Int = 0,
    val example : String? = null
)
