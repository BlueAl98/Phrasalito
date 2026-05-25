package com.nayibit.feature_languages.data.repository

import com.nayibit.feature_languages.data.remote.LanguagesApiService
import com.nayibit.feature_languages.data.remote.mapper.toDomain
import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.network.error.HttpErrorParser
import com.nayibit.network.error.NetworkError
import com.nayibit.utils.helpers.Result
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val apiService: LanguagesApiService
) : LanguageRepository {

    override suspend fun getLanguage(id: Int): Result<Language, NetworkError> {
        return try {
            Result.Success(apiService.getLanguage(id).toDomain())
        } catch (e: Exception) {
            Result.Error(HttpErrorParser.parse(e))
        }
    }
}
