package com.nayibit.feature_languages.data.remote.mapper

import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.database.room.entities.DeckEntity
import com.nayibit.database.room.entities.LanguageEntity
import com.nayibit.database.room.entities.PhraseEntity
import com.nayibit.feature_languages.data.remote.dto.CategoryDto
import com.nayibit.feature_languages.data.remote.dto.DeckDto
import com.nayibit.feature_languages.data.remote.dto.LanguageDto
import com.nayibit.feature_languages.data.remote.dto.PhraseDto
import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.model.LanguageStatus
import com.nayibit.feature_languages.domain.model.LanguageUi

fun LanguageDto.toDomain() = Language(
    id = id,
    code = code,
    name = name,
    flag = flag,
    status = status
)

fun LanguageDto.toEntity() = LanguageEntity(
    id = id,
    code = code,
    name = name,
    flag = flag,
    status = status,
    isDownload = false
)

fun LanguageEntity.toDomain() = Language(
    id = id,
    code = code,
    name = name,
    flag = flag,
    status = status,
    isDownload = isDownload
)

fun CategoryDto.toEntity(languageId: Int) = CategoryEntity(
    id = id,
    name = name,
    subtitle = subtitle,
    maxDecks = numDecks,
    languageId = languageId,
    icon = icon,
    isDefault = true
)

fun DeckDto.toEntity(lngCode: String, languageName: String) = DeckEntity(
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

fun Language.toUi() = LanguageUi(
    id = id,
    code = code,
    displayName = name,
    flagEmoji = flag,
    status = when {
        isDownload -> LanguageStatus.AVAILABLE
        status -> LanguageStatus.DOWNLOADABLE
        else -> LanguageStatus.COMING_SOON
    }
)
