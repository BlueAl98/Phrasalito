package com.nayibit.phrasalito_domain.useCases.tts

import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import javax.inject.Inject

class ShutDownTtsUseCase @Inject constructor(
    private val ttsRepository: TextSpeechRepository
) {
    operator fun invoke() =  ttsRepository.shutdownTts()
}