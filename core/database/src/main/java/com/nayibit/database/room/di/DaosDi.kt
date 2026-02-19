package com.nayibit.database.room.di

import com.nayibit.database.AppDatabase
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosDi {

    @Provides
    fun provideStartFeatureDao(database: AppDatabase): DeckDao {
        return database.deckDao()
    }

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

}