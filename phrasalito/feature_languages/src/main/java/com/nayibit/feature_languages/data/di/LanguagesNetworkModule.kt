package com.nayibit.feature_languages.data.di

import com.nayibit.feature_languages.data.remote.LanguagesApiService
import com.nayibit.feature_languages.data.repository.LanguageRepositoryImpl
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LanguagesApiModule {

    @Provides
    @Singleton
    fun provideLanguagesApiService(retrofit: Retrofit): LanguagesApiService =
        retrofit.create(LanguagesApiService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class LanguagesRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindLanguageRepository(impl: LanguageRepositoryImpl): LanguageRepository
}
