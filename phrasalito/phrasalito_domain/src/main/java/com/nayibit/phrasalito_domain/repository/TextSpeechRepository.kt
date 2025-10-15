package com.nayibit.phrasalito_domain.repository

import com.nayibit.common.util.Resource
import kotlinx.coroutines.flow.Flow
import java.util.Locale

interface TextSpeechRepository {

    fun speakText(text: String, langCode: String)

    fun shutdownTts()

    fun isTtsReady(): Flow<Resource<Boolean>>

   suspend fun getLanguagesSuported(): Flow<Resource<List<Locale>>>

}