package com.nayibit.phrasalito_data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.nayibit.common.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextToSpeechManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val _isReady = MutableStateFlow<Resource<Boolean>>(Resource.Loading)
    val isReady: Flow<Resource<Boolean>> = _isReady

    private val tts: TextToSpeech = TextToSpeech(context) { status ->
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                _isReady.value = Resource.Error("Language not supported")
            } else {
                _isReady.value = Resource.Success(true)
            }
        } else {
            Log.e("TTS", "Initialization failed")
            _isReady.value = Resource.Error("Initialization failed")
        }
    }

    fun speak(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "tts-${System.currentTimeMillis()}")
    }

    fun shutdown() {
        tts.stop()
        tts.shutdown()
    }
}