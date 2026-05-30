package com.nayibit.translation.data

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.nayibit.translation.domain.TranslationManager
import com.nayibit.translation.domain.error.TranslationError
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.translation.domain.model.TranslationLanguage
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class TranslationManagerImpl @Inject constructor() : TranslationManager {

    private val modelManager = RemoteModelManager.getInstance()

    override fun translate(text: String, sourceLanguage: String, targetLanguage: String): Flow<Result<String, TranslationError>> = flow {
        try {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage)
                .setTargetLanguage(targetLanguage)
                .build()
            val translator = Translation.getClient(options)
            val conditions = DownloadConditions.Builder().build()
            translator.downloadModelIfNeeded(conditions).await()
            val translated = translator.translate(text).await()
            translator.close()
            emit(Result.Success(translated))
        } catch (e: Exception) {
            emit(Result.Error(TranslationError.General(e.message ?: "Translation failed")))
        }
    }

    override fun downloadModel(languageCode: String): Flow<Result<ModelDownloadState, TranslationError>> = flow {
        try {
            emit(Result.Success(ModelDownloadState.Downloading))
            val model = TranslateRemoteModel.Builder(languageCode).build()
            val alreadyDownloaded = modelManager.isModelDownloaded(model).await()
            if (!alreadyDownloaded) {
                val conditions = DownloadConditions.Builder().build()
                modelManager.download(model, conditions).await()
            }
            emit(Result.Success(ModelDownloadState.Downloaded))
        } catch (e: Exception) {
            emit(Result.Error(TranslationError.General(e.message ?: "Download failed")))
        }
    }

    override fun deleteModel(languageCode: String): Flow<Result<Boolean, TranslationError>> = flow {
        try {
            val model = TranslateRemoteModel.Builder(languageCode).build()
            modelManager.deleteDownloadedModel(model).await()
            emit(Result.Success(true))
        } catch (e: Exception) {
            emit(Result.Error(TranslationError.General(e.message ?: "Delete failed")))
        }
    }

    override fun getDownloadedModels(): Flow<Result<List<TranslationLanguage>, TranslationError>> = flow {
        try {
            val models = modelManager.getDownloadedModels(TranslateRemoteModel::class.java).await()
            val languages = models.map { model ->
                TranslationLanguage(
                    code = model.language,
                    displayName = Locale(model.language).displayLanguage
                )
            }
            emit(Result.Success(languages))
        } catch (e: Exception) {
            emit(Result.Error(TranslationError.General(e.message ?: "Failed to get downloaded models")))
        }
    }

    override fun isModelDownloaded(languageCode: String): Flow<Result<Boolean, TranslationError>> = flow {
        try {
            val model = TranslateRemoteModel.Builder(languageCode).build()
            val isDownloaded = modelManager.isModelDownloaded(model).await()
            emit(Result.Success(isDownloaded))
        } catch (e: Exception) {
            emit(Result.Error(TranslationError.General(e.message ?: "Failed to check model")))
        }
    }
}
