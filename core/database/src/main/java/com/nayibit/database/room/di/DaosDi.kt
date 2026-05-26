package com.nayibit.database.room.di

import com.nayibit.database.AppDatabase
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import com.nayibit.database.room.dao.LanguageDao
import com.nayibit.database.room.dao.PhraseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DaosDi {

    @Provides
    fun provideCategoryDao(database: AppDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideDeckDao(database: AppDatabase): DeckDao {
        return database.deckDao()
    }

    @Provides
    fun providePhraseDao(database: AppDatabase): PhraseDao {
        return database.phraseDao()
    }

    @Provides
    fun provideLanguageDao(database: AppDatabase): LanguageDao {
        return database.languageDao()
    }
}