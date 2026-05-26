package com.nayibit.feature_languages.data.repository

import com.nayibit.database.room.dao.LanguageDao
import com.nayibit.feature_languages.data.remote.LanguagesApiService
import com.nayibit.feature_languages.data.remote.mapper.toDomain
import com.nayibit.feature_languages.data.remote.mapper.toEntity
import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.network.error.HttpErrorParser
import com.nayibit.network.error.NetworkError
import com.nayibit.translation.domain.TranslationManager
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Resource
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val apiService: LanguagesApiService,
    private val languageDao: LanguageDao,
    private val translationManager: TranslationManager
) : LanguageRepository {

    override suspend fun getLanguages(): Result<List<Language>, NetworkError> {
        return try {
            val downloadedCodes = languageDao.getAll()
                .filter { it.isDownload }
                .map { it.code }
                .toSet()

            val entities = apiService.getLanguages().map { dto ->
                dto.toEntity().copy(isDownload = dto.code in downloadedCodes)
            }
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

    override fun downloadLanguage(code: String): Flow<Resource<ModelDownloadState>> =
        translationManager.downloadModel(code)
            .onEach { resource ->
                if (resource is Resource.Success && resource.data == ModelDownloadState.Downloaded) {
                    languageDao.updateIsDownloaded(code)
                }
            }
}
