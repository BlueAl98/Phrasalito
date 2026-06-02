package com.nayibit.feature_phrases.domain.useCases

import com.nayibit.tts.domain.TtsManager
import javax.inject.Inject

class SpeakTextUseCase @Inject constructor(
    private val ttsManager: TtsManager
) {
    operator fun invoke(text: String, langCode: String) = ttsManager.speakText(text, langCode)
}
