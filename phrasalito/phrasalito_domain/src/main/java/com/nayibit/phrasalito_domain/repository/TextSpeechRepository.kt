package com.nayibit.phrasalito_domain.repository

interface TextSpeechRepository {

    fun SpeakText(text: String)

    fun shutdownTts()
}