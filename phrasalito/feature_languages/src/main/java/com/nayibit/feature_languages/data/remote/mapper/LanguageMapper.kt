package com.nayibit.feature_languages.data.remote.mapper

import com.nayibit.feature_languages.data.remote.dto.LanguageDto
import com.nayibit.feature_languages.domain.model.Language

fun LanguageDto.toDomain() = Language(
    id = id,
    code = code,
    name = name,
    flag = flag,
    status = status
)
