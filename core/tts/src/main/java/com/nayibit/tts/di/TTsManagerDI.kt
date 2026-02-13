package com.nayibit.tts.di

import com.nayibit.tts.data.TextToSpeechManager
import com.nayibit.tts.data.TtsManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TTsManagerDI {

    @Provides
    @Singleton
    fun provideTtsManager(ttsManager: TextToSpeechManager): TtsManagerImpl {
        return TtsManagerImpl(ttsManager)

    }

}