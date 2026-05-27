package com.nayibit.feature_categories.data.remote.mapper

import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.database.room.entities.DeckEntity
import com.nayibit.database.room.entities.PhraseEntity
import com.nayibit.feature_categories.data.remote.dto.CategoryDto
import com.nayibit.feature_categories.data.remote.dto.DeckWithPhrasesDto
import com.nayibit.feature_categories.data.remote.dto.PhraseDto

fun CategoryDto.toEntity(languageId: Int) = CategoryEntity(
    id = id,
    name = name,
    subtitle = subtitle,
    maxDecks = numDecks,
    languageId = languageId,
    icon = icon,
    isDefault = true
)

fun DeckWithPhrasesDto.toEntity(lngCode: String, languageName: String) = DeckEntity(
    id = id,
    idCategory = categoryId,
    name = name,
    maxCards = maxCards,
    lngCode = lngCode,
    languageName = languageName
)

fun PhraseDto.toEntity(deckId: Int) = PhraseEntity(
    id = id,
    targetLanguage = targetLanguage,
    translation = translation,
    deckId = deckId,
    example = example
)
