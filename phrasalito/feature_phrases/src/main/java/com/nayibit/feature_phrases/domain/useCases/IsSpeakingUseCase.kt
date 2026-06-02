package com.nayibit.feature_phrases.domain.useCases

import com.nayibit.tts.domain.TtsManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsSpeakingUseCase @Inject constructor(
    private val ttsManager: TtsManager
) {
    suspend operator fun invoke(): Flow<Boolean> = ttsManager.isSpeaking()
}
