package com.nayibit.phrasalito_presentation.mappers

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUI

fun Deck.toDeckUI(): DeckUI{
    return DeckUI(
        id = this.id,
        name = this.name,
        maxCards = this.maxCards,
        lngCode = this.lngCode,
        languageName = this.languageName,
        isNotified = this.isNotified
    )
}

fun DeckUI.toDeck(): Deck{
    return Deck(
        id = this.id,
        name = this.name,
        maxCards = this.maxCards,
        lngCode = this.lngCode,
        languageName = this.languageName,
        isNotified = this.isNotified
    )
}