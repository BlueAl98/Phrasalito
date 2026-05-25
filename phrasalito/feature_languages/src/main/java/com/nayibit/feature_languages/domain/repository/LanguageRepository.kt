package com.nayibit.feature_languages.domain.repository

import com.nayibit.feature_languages.domain.model.Language

interface LanguageRepository {
    suspend fun getLanguage(id: Int): Language
}
