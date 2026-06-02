package com.nayibit.tts.domain

import com.nayibit.tts.utils.TtsError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface TtsManager {

    fun speakText(text: String, langCode: String)

    fun shutdownTts()

    suspend fun isTtsReady(): Flow<Result<Boolean, TtsError>>

    suspend fun isSpeaking(): Flow<Boolean>

    suspend fun getLanguagesSuported(): Flow<Result<List<Locale>, TtsError>>


}