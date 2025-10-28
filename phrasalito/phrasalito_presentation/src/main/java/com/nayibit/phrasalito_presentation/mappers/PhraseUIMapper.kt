package com.nayibit.phrasalito_presentation.mappers

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUi
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd

fun Phrase.toPhraseUi(): PhraseUi {
    return PhraseUi(
        id = this.id,
        targetLanguage = this.targetLanguage,
        translation = this.translation,
        isOptionsRevealed = false,
        example = this.example,
        color = primaryGradientEnd
            /*listOf(
            Color(0xFF764BA2),
            Color(0xFF8C63B3),
            Color(0xFF5F3F89),
            Color(0xFF4E2F75),
            Color(0xFF6B5BBF),
            Color(0xFF8E4BAE),
            Color(0xFF9B4B9E)
        ).random()*/
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