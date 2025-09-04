package com.nayibit.phrasalito_data.repository.di

import com.nayibit.phrasalito_data.dao.DeckDao
import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.repository.DeckRepositoryImpl
import com.nayibit.phrasalito_data.repository.PhraseRepositoryImpl
import com.nayibit.phrasalito_data.repository.TextSpeechRepositoryImpl
import com.nayibit.phrasalito_data.tts.TextToSpeechManager
import com.nayibit.phrasalito_domain.repository.DeckRepository
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import com.nayibit.phrasalito_domain.repository.TextSpeechRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    fun providePhraseRepository(phraseDao: PhraseDao): PhraseRepository {
        return PhraseRepositoryImpl(phraseDao)
    }

    @Provides
    fun provideDeckRepository(deckDao: DeckDao): DeckRepository {
        return DeckRepositoryImpl(deckDao)
    }

    @Provides
    fun provideTextToSpeechRepository(textToSpeechManager: TextToSpeechManager): TextSpeechRepository {
        return TextSpeechRepositoryImpl(textToSpeechManager)
    }



}