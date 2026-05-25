package com.nayibit.feature_languages.data.repository

import com.nayibit.feature_languages.data.remote.LanguagesApiService
import com.nayibit.feature_languages.data.remote.mapper.toDomain
import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val apiService: LanguagesApiService
) : LanguageRepository {

    override suspend fun getLanguage(id: Int): Language =
        apiService.getLanguage(id).toDomain()
}
