package com.nayibit.phrasalito_data.repository.di

import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.repository.PhraseRepositoryImpl
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PhraseRepositoryModule {

    @Provides
    fun providePhraseRepository(phraseDao: PhraseDao): PhraseRepository {
        return PhraseRepositoryImpl(phraseDao)
    }

}