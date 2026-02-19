package com.nayibit.feature_deckscreen.domain.repositories

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.model.DeckWithPhrases
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
   suspend fun insert(deck: Deck): Flow<Resource<Deck>>
    fun getAllDecks(): Flow<Resource<List<Deck>>>
   suspend fun deleteDeck(id: Int): Resource<Unit>
   suspend fun updateDeck(deck: Deck): Resource<Unit>
   suspend fun getPhrasesForNotification(): Resource<List<DeckWithPhrases?>>
   suspend fun createInitialDeck(): Resource<Boolean>
}