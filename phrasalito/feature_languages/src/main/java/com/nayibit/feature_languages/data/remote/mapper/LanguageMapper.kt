package com.nayibit.feature_languages.data.remote.mapper

import com.nayibit.database.room.entities.LanguageEntity
import com.nayibit.feature_languages.data.remote.dto.LanguageDto
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
