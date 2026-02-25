package com.nayibit.feature_phrases.di

import com.nayibit.database.room.dao.PhraseDao
import com.nayibit.feature_phrases.data.repositories.PhraseRepositoryImpl
import com.nayibit.feature_phrases.domain.repositories.PhraseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryDI {

    @Provides
    fun providePhraseRepository(phraseDao: PhraseDao): PhraseRepository {
        return PhraseRepositoryImpl(phraseDao)
    }


}