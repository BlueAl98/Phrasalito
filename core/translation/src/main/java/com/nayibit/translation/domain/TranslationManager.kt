package com.nayibit.translation.domain

import com.nayibit.translation.domain.error.TranslationError
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.translation.domain.model.TranslationLanguage
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface TranslationManager {

    fun translate(text: String, sourceLanguage: String, targetLanguage: String): Flow<Result<String, TranslationError>>

    fun downloadModel(languageCode: String): Flow<Result<ModelDownloadState, TranslationError>>

    fun deleteModel(languageCode: String): Flow<Result<Boolean, TranslationError>>

    fun getDownloadedModels(): Flow<Result<List<TranslationLanguage>, TranslationError>>

    fun isModelDownloaded(languageCode: String): Flow<Result<Boolean, TranslationError>>
}
