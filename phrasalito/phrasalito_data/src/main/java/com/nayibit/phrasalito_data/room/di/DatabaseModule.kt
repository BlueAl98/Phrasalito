package com.nayibit.phrasalito_data.room.di

import android.content.Context
import androidx.room.Room
import com.nayibit.common.util.Constants
import com.nayibit.phrasalito_data.dao.DeckDao
import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.room.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun providePhraseDao(appDatabase: AppDatabase): PhraseDao {
        return appDatabase.phraseDao()
    }

    @Provides
    fun provideDeckDao(appDatabase: AppDatabase): DeckDao {
        return appDatabase.deckDao()
    }

}