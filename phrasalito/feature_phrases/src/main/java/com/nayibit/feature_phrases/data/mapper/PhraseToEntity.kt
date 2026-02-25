package com.nayibit.feature_phrases.data.mapper

import com.nayibit.database.room.entities.PhraseEntity
import com.nayibit.feature_phrases.domain.model.Phrase

fun Phrase.toEntity() = PhraseEntity(
    id = id,
    targetLanguage = targetLanguage,
    translation = translation,
    deckId = deckId,
    example = example)