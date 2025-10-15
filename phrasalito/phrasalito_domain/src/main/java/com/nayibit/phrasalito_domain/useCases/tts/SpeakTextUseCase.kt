package com.nayibit.phrasalito_domain.useCases.tts

import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import javax.inject.Inject

class SpeakTextUseCase
@Inject constructor(private val textToSpeechRepository: TextSpeechRepository) {
    operator fun invoke(text: String, langCode: String) = textToSpeechRepository.speakText(text, langCode)
}