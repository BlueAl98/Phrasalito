package com.nayibit.tts.data

import com.nayibit.tts.domain.TtsManager
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject

class TtsManagerImpl @Inject constructor(
    private val ttsManager: TextToSpeechManager
) : TtsManager {

    override fun speakText(text: String, langCode: String) {
        TODO("Not yet implemented")
    }

    override fun shutdownTts() {
        TODO("Not yet implemented")
    }

    override suspend fun isTtsReady(): Flow<Resource<Boolean>> {
        return ttsManager.isReady
    }

    override suspend fun getLanguagesSuported(): Flow<Resource<List<Locale>>> {
        return flow {
            try {
               emit(Resource.Success(ttsManager.getAvailableLanguages()))
            }catch (e: Exception){
                emit(Resource.Error(e.message ?: "Unknown error"))
            }
        }
    }
}