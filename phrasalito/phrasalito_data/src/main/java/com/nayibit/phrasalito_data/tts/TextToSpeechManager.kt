package com.nayibit.phrasalito_data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
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
    private val _isReady = MutableStateFlow<Resource<Boolean>>(Resource.Success(false))
    val isReady: StateFlow<Resource<Boolean>> = _isReady.asStateFlow()


    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking.asStateFlow()

    private lateinit var tts: TextToSpeech

    init {
        initTts()
    }


    private fun initTts() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                _isReady.value = Resource.Success(true)
            } else {
                _isReady.value = Resource.Error("Initialization failed")
            }
        }

        // Track when TTS is currently speaking (API 26+)
        tts.setOnUtteranceProgressListener(object : android.speech.tts.UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                _isSpeaking.value = true
            }

            override fun onDone(utteranceId: String?) {
                _isSpeaking.value = false
            }

            override fun onError(utteranceId: String?) {
                _isSpeaking.value = false
            }
        })

    }

    fun speak(text: String, langCode: Locale = Locale.US) {
        if (!::tts.isInitialized) {
            _isReady.value = Resource.Error("TTS not initialized")
            return
        }

        val result = tts.setLanguage(langCode)

        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            _isReady.value = Resource.Error("Language not supported: $langCode")
            return
        }

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts-${System.currentTimeMillis()}")
    }

    fun shutdown() {
        if (::tts.isInitialized) {
            tts.stop()
            tts.shutdown()
        }
    }

    fun getAvailableLanguages(): Flow<Resource<List<Locale>>> = flow {
        try {
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
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Unknown error"))
        }
    }

}