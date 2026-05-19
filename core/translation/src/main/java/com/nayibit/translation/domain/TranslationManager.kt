package com.nayibit.translation.domain

import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.translation.domain.model.TranslationLanguage
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface TranslationManager {

    fun translate(text: String, sourceLanguage: String, targetLanguage: String): Flow<Resource<String>>

    fun downloadModel(languageCode: String): Flow<Resource<ModelDownloadState>>

    fun deleteModel(languageCode: String): Flow<Resource<Boolean>>

    fun getDownloadedModels(): Flow<Resource<List<TranslationLanguage>>>

    fun isModelDownloaded(languageCode: String): Flow<Resource<Boolean>>
}
