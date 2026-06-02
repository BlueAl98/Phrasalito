package com.nayibit.tts.data

import com.nayibit.tts.domain.TtsManager
import com.nayibit.tts.utils.TtsError
import com.nayibit.utils.helpers.Result
import com.nayibit.utils.helpers.toLocale
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class TtsManagerImpl @Inject constructor(
    private val ttsManager: TextToSpeechManager
) : TtsManager {

    override fun speakText(text: String, langCode: String) {
        ttsManager.speak(text, langCode.toLocale())
    }

    override fun shutdownTts() {
        TODO("Not yet implemented")
    }

    override suspend fun isTtsReady(): Flow<Result<Boolean, TtsError>> {
        return ttsManager.isReady
    }

    override suspend fun isSpeaking(): Flow<Boolean> {
        return ttsManager.isSpeaking
    }

    override suspend fun getLanguagesSuported(): Flow<Result<List<Locale>, TtsError>> {
        return flow {
            try {
                emit(Result.Success(ttsManager.getAvailableLanguages()))
            } catch (e: Exception) {
                emit(Result.Error(TtsError.InitializationFailed))
            }
        }
    }
}