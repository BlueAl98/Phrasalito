package com.nayibit.phrasalito_data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.nayibit.common.util.Constants.LANGUAGE
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_data.dataStore.GenericDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechManager @Inject constructor(
    @ApplicationContext context: Context,
    private val dataStore: GenericDataStore
) {
    private val _isReady = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isReady: Flow<Resource<Boolean>> = _isReady

    private val tts: TextToSpeech

    init {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                // Launch coroutine to get saved language from DataStore
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        // Get saved language (default to English if not set)
                       val savedLang = dataStore
                            .getData(LANGUAGE, "en_US")
                            .first()

                        Log.d("TTS", "Saved language: $savedLang")

                        val locale = Locale(savedLang)
                        val result = tts.setLanguage(locale)

                        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "Language not supported: $savedLang")
                            _isReady.value = Resource.Error("Language not supported: $savedLang")
                        } else {
                            Log.d("TTS", "Language set to $savedLang (${locale.displayName})")
                            _isReady.value = Resource.Success(true)
                        }
                    } catch (e: Exception) {
                        Log.e("TTS", "Error loading language: ${e.message}")
                        _isReady.value = Resource.Error("Failed to load language: ${e.message}")
                    }
                }
            } else {
                Log.e("TTS", "Initialization failed")
                _isReady.value = Resource.Error("Initialization failed")
            }
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts-${System.currentTimeMillis()}")
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }

    /*
    fun getAvailableLanguages(): Flow<Resource<List<Locale>>> = flow {

        return try {
            tts.availableLanguages
                ?.filter { tts.isLanguageAvailable(it) >= TextToSpeech.LANG_AVAILABLE }
                ?.sortedBy { it.displayName }
                ?: emptyList()

        }

      /*  return try {
            tts.availableLanguages
                ?.filter { tts.isLanguageAvailable(it) >= TextToSpeech.LANG_AVAILABLE }
                ?.sortedBy { it.displayName }
                ?: emptyList()
        } catch (e: Exception) {
            Log.e("TTS", "Error fetching languages: ${e.message}")
            emptyList()
        }*/
    }
  */

fun getAvailableLanguages(): Flow<Resource<List<Locale>>> = flow {
    try {
        emit(Resource.Loading)

        val availableLanguages = tts.availableLanguages
            ?.filter { tts.isLanguageAvailable(it) >= TextToSpeech.LANG_AVAILABLE }
            ?.sortedBy { it.displayName }
            ?: emptyList()

        emit(Resource.Success(availableLanguages))
    } catch (e: Exception) {
        Log.e("TTS", "Error fetching languages: ${e.message}")
        emit(Resource.Error(e.message ?: "Unknown error"))
    }
}


}