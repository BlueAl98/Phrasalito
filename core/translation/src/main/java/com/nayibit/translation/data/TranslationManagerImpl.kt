package com.nayibit.translation.data

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.nayibit.translation.domain.TranslationManager
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.translation.domain.model.TranslationLanguage
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.util.Locale
import javax.inject.Inject

class TranslationManagerImpl @Inject constructor() : TranslationManager {

    private val modelManager = RemoteModelManager.getInstance()

    override fun translate(text: String, sourceLanguage: String, targetLanguage: String): Flow<Resource<String>> = flow {
        try {
            val options = TranslatorOptions.Builder()
                .setSourceLanguage(sourceLanguage)
                .setTargetLanguage(targetLanguage)
                .build()
            val translator = Translation.getClient(options)
            val conditions = DownloadConditions.Builder().build()
            translator.downloadModelIfNeeded(conditions).await()
            val result = translator.translate(text).await()
            translator.close()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Translation failed"))
        }
    }

    override fun downloadModel(languageCode: String): Flow<Resource<ModelDownloadState>> = flow {
        try {
            emit(Resource.Success(ModelDownloadState.Downloading))
            val model = TranslateRemoteModel.Builder(languageCode).build()
            val conditions = DownloadConditions.Builder().build()
            modelManager.download(model, conditions).await()
            emit(Resource.Success(ModelDownloadState.Downloaded))
        } catch (e: Exception) {
            emit(Resource.Success(ModelDownloadState.Error(e.message ?: "Download failed")))
        }
    }

    override fun deleteModel(languageCode: String): Flow<Resource<Boolean>> = flow {
        try {
            val model = TranslateRemoteModel.Builder(languageCode).build()
            modelManager.deleteDownloadedModel(model).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Delete failed"))
        }
    }

    override fun getDownloadedModels(): Flow<Resource<List<TranslationLanguage>>> = flow {
        try {
            val models = modelManager.getDownloadedModels(TranslateRemoteModel::class.java).await()
            val languages = models.map { model ->
                TranslationLanguage(
                    code = model.language,
                    displayName = Locale(model.language).displayLanguage
                )
            }
            emit(Resource.Success(languages))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to get downloaded models"))
        }
    }

    override fun isModelDownloaded(languageCode: String): Flow<Resource<Boolean>> = flow {
        try {
            val model = TranslateRemoteModel.Builder(languageCode).build()
            val isDownloaded = modelManager.isModelDownloaded(model).await()
            emit(Resource.Success(isDownloaded))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Failed to check model"))
        }
    }
}
