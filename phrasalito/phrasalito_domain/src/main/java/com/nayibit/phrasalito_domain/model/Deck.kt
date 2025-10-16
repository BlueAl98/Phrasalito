package com.nayibit.phrasalito_domain.model

data class Deck(
    val id: Int = 0,
    val name: String,
    val maxCards: Int,
    val lngCode: String,
    val languageName: String,
    val isNotified: Boolean = false
)

