package com.nayibit.tts.utils

import com.nayibit.utils.helpers.Error

sealed class TtsError : Error {
    data object NotInitialized : TtsError()
    data object InitializationFailed : TtsError()
    data class LanguageNotSupported(val langCode: String) : TtsError()
}