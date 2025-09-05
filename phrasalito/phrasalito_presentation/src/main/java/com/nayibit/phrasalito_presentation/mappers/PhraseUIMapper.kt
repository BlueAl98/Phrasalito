package com.nayibit.phrasalito_presentation.mappers

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUi

fun Phrase.toPhraseUi(): PhraseUi {
    return PhraseUi(
        id = this.id,
        targetLanguage = this.targetLanguage,
        translation = this.translation,
        isOptionsRevealed = false,
        example = this.example ?: ""
    )
}

fun PhraseUi.toPhrase(): Phrase {
    return Phrase(
        id = this.id,
        targetLanguage = this.targetLanguage,
        translation = this.translation,
        deckId = -1,
        example = this.example
    )
}