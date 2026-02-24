package com.nayibit.feature_deckscreen.domain.model

data class Deck(
    val id: Int = 0,
    val idCategory: Int,
    val name: String,
    val maxCards: Int = 20,
    val lngCode: String,
    val languageName: String,
    val isNotified: Boolean = false,
    val currentPhrases: Int = 0
)
