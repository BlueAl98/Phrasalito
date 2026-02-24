package com.nayibit.feature_deckscreen.di

import com.nayibit.database.room.dao.DeckDao
import com.nayibit.feature_deckscreen.data.repositories.DeckRepositoryImpl
import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoryDeckDI {

    @Provides
    fun provideDeckRepository(deckDao: DeckDao): DeckRepository {
        return DeckRepositoryImpl(deckDao)
    }

}