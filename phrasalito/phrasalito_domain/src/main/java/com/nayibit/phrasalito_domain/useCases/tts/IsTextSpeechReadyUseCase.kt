package com.nayibit.phrasalito_domain.useCases.tts

import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import javax.inject.Inject

class IsTextSpeechReadyUseCase @Inject constructor(private val textToSpeechRepository: TextSpeechRepository) {
   suspend  operator fun invoke() =
        textToSpeechRepository.isTtsReady()
}