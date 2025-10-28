package com.nayibit.phrasalito_data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.nayibit.common.util.Constants.LIST_OF_LANGUAGES
import com.nayibit.common.util.Constants.NUM_OF_LANGUAGES
import com.nayibit.common.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TextToSpeechManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val _isReady = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isReady: StateFlow<Resource<Boolean>> = _isReady.asStateFlow()

    private lateinit var tts: TextToSpeech

    init {
        initTts()
    }

    private fun initTts() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                Log.d("TTS", "TTS initialized successfully")
                _isReady.value = Resource.Success(true)
            } else {
                Log.e("TTS", "Initialization failed")
                _isReady.value = Resource.Error("Initialization failed")
            }
        }
    }

    fun speak(text: String, langCode: String = "en_US") {
        if (!::tts.isInitialized) {
            Log.e("TTS", "TTS not initialized")
            _isReady.value = Resource.Error("TTS not initialized")
            return
        }

        val locale = Locale(langCode)
        val result = tts.setLanguage(locale)

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            Log.e("TTS", "Language not supported: $langCode")
            _isReady.value = Resource.Error("Language not supported: $langCode")
            return
        }

        Log.d("TTS", "Speaking in language: $langCode (${locale.displayName})")
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts-${System.currentTimeMillis()}")
    }

    fun shutdown() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }
/*
    fun getAvailableLanguages(): Flow<Resource<List<Locale>>> = flow {
        try {
            emit(Resource.Loading)
            val allLanguages = tts.availableLanguages
                ?.filter { tts.isLanguageAvailable(it) >= TextToSpeech.LANG_AVAILABLE }
                ?.distinctBy { it.language }
                ?: emptyList()

            val prioritized = allLanguages.filter { LIST_OF_LANGUAGES.contains(it.language) }
            val remaining = allLanguages.filterNot { LIST_OF_LANGUAGES.contains(it.language) }

            emit(Resource.Success((prioritized + remaining).take(NUM_OF_LANGUAGES)))
        } catch (e: Exception) {
            Log.e("TTS", "Error fetching languages: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

*/

    fun getAvailableLanguages(): Flow<Resource<List<Locale>>> = flow {
        try {
            emit(Resource.Loading)

            // Get all available and valid TTS languages on the device
            val allLanguages = tts.availableLanguages
                ?.filter { tts.isLanguageAvailable(it) >= TextToSpeech.LANG_AVAILABLE }
                ?.distinctBy { it.language }
                ?: emptyList()

            // Keep only the ones from your approved list
            val filtered = allLanguages.filter { locale ->
                LIST_OF_LANGUAGES.contains(locale.language)
            }

            // Emit only your supported and available languages
            emit(Resource.Success(filtered.take(NUM_OF_LANGUAGES)))

            // Optional: Log missing voices (helpful for debugging)
            val missing = LIST_OF_LANGUAGES.filterNot { code ->
                allLanguages.any { it.language == code }
            }
            if (missing.isNotEmpty()) {
                Log.w("TTS", "Missing TTS voices for: $missing")
            }

        } catch (e: Exception) {
            Log.e("TTS", "Error fetching languages: ${e.message}")
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

}