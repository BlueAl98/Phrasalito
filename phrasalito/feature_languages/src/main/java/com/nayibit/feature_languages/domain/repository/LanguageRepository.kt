package com.nayibit.feature_languages.domain.repository

import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.network.error.NetworkError
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Resource
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    suspend fun getLanguages(): Result<List<Language>, NetworkError>
    fun downloadLanguage(code: String): Flow<Resource<ModelDownloadState>>
}
