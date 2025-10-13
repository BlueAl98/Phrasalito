package com.nayibit.phrasalito_domain.useCases.tts

import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import java.util.Locale
import javax.inject.Inject

class GetAvailableLanguagesUseCase @Inject constructor(
    private val textToSpeechRepository: TextSpeechRepository
) {
    operator fun invoke(): List<Locale> {
        return textToSpeechRepository.getLanguagesSuported()
    }

}