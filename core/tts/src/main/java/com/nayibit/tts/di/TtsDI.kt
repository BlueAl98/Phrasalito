package com.nayibit.tts.di

import android.content.Context
import com.nayibit.tts.data.TextToSpeechManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TtsDI {

    @Provides
    @Singleton
    fun provideTtsContext(@ApplicationContext context: Context): TextToSpeechManager {
        return TextToSpeechManager(context)

    }
}