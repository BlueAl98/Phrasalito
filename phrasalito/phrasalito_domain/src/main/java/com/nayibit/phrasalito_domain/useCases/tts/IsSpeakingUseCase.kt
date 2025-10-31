package com.nayibit.phrasalito_domain.useCases.tts

import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class IsSpeakingUseCase @Inject constructor(
    private val repository: TextSpeechRepository
) {
    suspend operator fun invoke(): StateFlow<Boolean> = repository.isTtsSpeaking()
}