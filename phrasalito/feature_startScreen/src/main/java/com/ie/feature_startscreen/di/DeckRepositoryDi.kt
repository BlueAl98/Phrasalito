package com.ie.feature_startscreen.di

import com.ie.feature_startscreen.data.repositories.DeckRepositoryImpl
import com.ie.feature_startscreen.domain.repositories.DeckRepository
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DeckRepositoryDi {

    @Provides
    fun provideDeckRepository(dao: CategoryDao) : DeckRepository {
        return DeckRepositoryImpl(dao)
    }

}