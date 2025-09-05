package com.nayibit.phrasalito_data.repository

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_data.tts.TextToSpeechManager
import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TextSpeechRepositoryImpl
@Inject constructor(private val tts: TextToSpeechManager) : TextSpeechRepository {

    override fun SpeakText(text: String) {
        tts.speak(text)
    }

    override fun shutdownTts() {
        tts.shutdown()
    }

    override fun isTtsReady(): Flow<Resource<Boolean>> =
        tts.isReady

}