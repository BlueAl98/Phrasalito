package com.nayibit.feature_languages.data.repository

import com.nayibit.database.room.dao.LanguageDao
import com.nayibit.feature_languages.data.remote.LanguagesApiService
import com.nayibit.feature_languages.data.remote.mapper.toDomain
import com.nayibit.feature_languages.data.remote.mapper.toEntity
import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.network.error.HttpErrorParser
import com.nayibit.network.error.NetworkError
import com.nayibit.utils.helpers.Result
import javax.inject.Inject
import kotlin.collections.map

class LanguageRepositoryImpl @Inject constructor(
    private val apiService: LanguagesApiService,
    private val languageDao: LanguageDao
) : LanguageRepository {

    override suspend fun getLanguages(): Result<List<Language>, NetworkError> {
        return try {
            val entities = apiService.getLanguages().map { it.toEntity() }
            languageDao.insertAll(entities)
            Result.Success(entities.map { it.toDomain() })
        } catch (e: Exception) {
            val cached = languageDao.getAll()
            if (cached.isNotEmpty()) {
                Result.Success(cached.map { it.toDomain() })
            } else {
                Result.Error(HttpErrorParser.parse(e))
            }
        }
    }
}
