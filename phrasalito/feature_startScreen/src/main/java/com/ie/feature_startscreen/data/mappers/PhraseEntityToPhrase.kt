package com.ie.feature_startscreen.data.mappers

import com.ie.feature_startscreen.domain.model.Phrase
import com.nayibit.database.room.entities.PhraseEntity

fun PhraseEntity.toPhrase(): Phrase {
    return Phrase(
        id = id,
        targetLanguage = targetLanguage,
        translation = translation,
        deckId = deckId,
        isNotified = isNotified,
        example = example
    )
}