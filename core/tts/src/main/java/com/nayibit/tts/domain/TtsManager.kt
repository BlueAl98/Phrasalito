package com.nayibit.tts.domain

import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface TtsManager {

    fun speakText(text: String, langCode: String)

    fun shutdownTts()

    suspend fun isTtsReady(): Flow<Resource<Boolean>>

    suspend fun getLanguagesSuported(): Flow<Resource<List<Locale>>>


}