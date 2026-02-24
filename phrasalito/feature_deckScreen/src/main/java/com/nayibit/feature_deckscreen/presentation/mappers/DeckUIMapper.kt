package com.nayibit.feature_deckscreen.presentation.mappers

import com.nayibit.feature_deckscreen.domain.model.Deck
import com.nayibit.feature_deckscreen.presentation.deckScreen.DeckUI
import com.nayibit.feature_deckscreen.presentation.model.Language

fun Deck.toDeckUI(): DeckUI {
    return DeckUI(
        id = this.id,
        name = this.name,
        maxCards = this.maxCards,
        numCards = /*this.currentCards*/ 0,
        isNotified = this.isNotified,
        selectedLanguage = Language(this.id, this.languageName, this.lngCode),
        idCategory = this.idCategory
    )
}

fun DeckUI.toDeck(): Deck{
    return Deck(
        id = this.id,
        name = this.name,
        maxCards = this.numCards,
        lngCode = this.selectedLanguage?.alias ?: "",
        languageName = this.selectedLanguage?.language ?: "",
        isNotified = this.isNotified,
        idCategory = this.idCategory
    )
}