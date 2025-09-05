package com.nayibit.phrasalito_domain.repository

import com.nayibit.common.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface TextSpeechRepository {

    fun SpeakText(text: String)

    fun shutdownTts()

    fun isTtsReady(): Flow<Resource<Boolean>>

}