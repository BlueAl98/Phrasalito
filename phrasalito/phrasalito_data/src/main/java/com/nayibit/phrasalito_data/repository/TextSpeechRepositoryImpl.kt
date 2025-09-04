package com.nayibit.phrasalito_data.repository

import com.nayibit.phrasalito_data.tts.TextToSpeechManager
import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import javax.inject.Inject

class TextSpeechRepositoryImpl
@Inject constructor(private val tts: TextToSpeechManager) : TextSpeechRepository {

    override fun SpeakText(text: String) {
        tts.speak(text)
    }

    override fun shutdownTts() {
        tts.shutdown()
    }
}