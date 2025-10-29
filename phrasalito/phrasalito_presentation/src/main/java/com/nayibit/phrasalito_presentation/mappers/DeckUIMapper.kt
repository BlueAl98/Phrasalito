package com.nayibit.phrasalito_presentation.mappers

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_presentation.model.Language
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUI

fun Deck.toDeckUI(): DeckUI{
    return DeckUI(
        id = this.id,
        name = this.name,
        maxCards = this.maxCards,
        numCards = this.currentCards,
        isNotified = this.isNotified,
        selectedLanguage = Language(this.id,this.languageName, this.lngCode)
    )
}

fun DeckUI.toDeck(): Deck{
    return Deck(
        id = this.id,
        name = this.name,
        maxCards = this.numCards,
        lngCode = this.selectedLanguage?.alias ?: "",
        languageName = this.selectedLanguage?.language ?: "",
        isNotified = this.isNotified
    )
}