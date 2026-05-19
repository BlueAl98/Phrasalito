package com.nayibit.translation.di

import com.nayibit.translation.data.TranslationManagerImpl
import com.nayibit.translation.domain.TranslationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TranslationModule {

    @Provides
    @Singleton
    fun provideTranslationManager(): TranslationManager {
        return TranslationManagerImpl()
    }
}
