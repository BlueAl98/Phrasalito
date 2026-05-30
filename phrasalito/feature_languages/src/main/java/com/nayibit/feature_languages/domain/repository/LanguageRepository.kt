package com.nayibit.feature_languages.domain.repository

import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.network.error.NetworkError
import com.nayibit.translation.domain.error.TranslationError
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun getLanguages(): Flow<Result<List<Language>, NetworkError>>
    fun downloadLanguage(code: String): Flow<Result<ModelDownloadState, TranslationError>>
}
