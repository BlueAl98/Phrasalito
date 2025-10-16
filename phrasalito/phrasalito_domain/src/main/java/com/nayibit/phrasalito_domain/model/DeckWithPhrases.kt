package com.nayibit.phrasalito_domain.model

data class DeckWithPhrases(
    val deck: Deck,
    val phrases: List<Phrase>
)