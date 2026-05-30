package com.nayibit.feature_languages.data.repository

import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import com.nayibit.database.room.dao.LanguageDao
import com.nayibit.database.room.dao.PhraseDao
import com.nayibit.feature_languages.data.remote.LanguagesApiService
import com.nayibit.feature_languages.data.remote.mapper.toDomain
import com.nayibit.feature_languages.data.remote.mapper.toEntity
import com.nayibit.feature_languages.domain.model.Language
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.network.error.HttpErrorParser
import com.nayibit.network.error.NetworkError
import com.nayibit.translation.domain.TranslationManager
import com.nayibit.translation.domain.error.TranslationError
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LanguageRepositoryImpl @Inject constructor(
    private val apiService: LanguagesApiService,
    private val languageDao: LanguageDao,
    private val categoryDao: CategoryDao,
    private val deckDao: DeckDao,
    private val phraseDao: PhraseDao,
    private val translationManager: TranslationManager
) : LanguageRepository {

    override fun getLanguages(): Flow<Result<List<Language>, NetworkError>> = flow {
        try {
            val downloadedCodes = languageDao.getAll().first()
                .filter { it.isDownload }
                .map { it.code }
                .toSet()

            val dtos = apiService.getLanguages()

            val languageEntities = dtos.map { dto ->
                dto.toEntity().copy(isDownload = dto.code in downloadedCodes)
            }
            languageDao.insertAll(languageEntities)

            val categoryEntities = dtos.flatMap { dto ->
                dto.categories.map { it.toEntity(languageId = dto.id) }
            }
            if (categoryEntities.isNotEmpty()) categoryDao.insertCategories(categoryEntities)

            val deckEntities = dtos.flatMap { dto ->
                dto.categories.flatMap { category ->
                    category.decks.map { it.toEntity(lngCode = dto.code, languageName = dto.name) }
                }
            }
            if (deckEntities.isNotEmpty()) deckDao.insertAll(deckEntities)

            val phraseEntities = dtos.flatMap { dto ->
                dto.categories.flatMap { category ->
                    category.decks.flatMap { deck ->
                        deck.phrases.map { it.toEntity(deckId = deck.id) }
                    }
                }
            }
            if (phraseEntities.isNotEmpty()) phraseDao.insertAll(phraseEntities)

        } catch (e: Exception) {
            val cached = languageDao.getAll().first()
            if (cached.isEmpty()) {
                emit(Result.Error(HttpErrorParser.parse(e)))
                return@flow
            }
        }

        emitAll(
            languageDao.getLanguages().map { list ->
                Result.Success(list.map { it.language.toDomain() })
            }
        )
    }

    override fun downloadLanguage(code: String): Flow<Result<ModelDownloadState, TranslationError>> = flow {
        translationManager.downloadModel(code).collect { result ->
            if (result is Result.Success && result.data == ModelDownloadState.Downloaded) {
                languageDao.updateIsDownloaded(code)
            }
            emit(result)
        }
    }
}
