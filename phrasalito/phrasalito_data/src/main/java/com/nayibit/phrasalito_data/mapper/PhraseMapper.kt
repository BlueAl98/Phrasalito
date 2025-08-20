package com.nayibit.phrasalito_data.mapper

import com.nayibit.phrasalito_data.entities.PhraseEntity
import com.nayibit.phrasalito_domain.model.Phrase

fun PhraseEntity.toDomain() : Phrase{
    return Phrase(
        id = id,
        targetLanguage = targetLanguage,
        translation = translation,
        deckId = deckId)
}

fun Phrase.toEntity() : PhraseEntity{
    return PhraseEntity(
        id = id,
        targetLanguage = targetLanguage,
        translation = translation,
        deckId = deckId)
}