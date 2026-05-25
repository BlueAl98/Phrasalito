package com.nayibit.feature_languages.domain.repository

import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.network.error.NetworkError
import com.nayibit.utils.helpers.Result

interface LanguageRepository {
    suspend fun getLanguage(id: Int): Result<Language, NetworkError>
}
