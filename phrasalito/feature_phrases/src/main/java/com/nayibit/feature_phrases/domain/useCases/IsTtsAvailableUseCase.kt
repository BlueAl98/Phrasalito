package com.nayibit.feature_phrases.domain.useCases

import com.nayibit.tts.domain.TtsManager
import com.nayibit.tts.utils.TtsError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsTtsAvailableUseCase @Inject constructor(
    private val ttsManager: TtsManager
) {
    suspend operator fun invoke(): Flow<Result<Boolean, TtsError>> = ttsManager.isTtsReady()
}
