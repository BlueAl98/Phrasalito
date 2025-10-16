package com.nayibit.phrasalito_domain.repository

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.model.DeckWithPhrases
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
   suspend fun insert(deck: Deck): Flow<Resource<Deck>>
   suspend fun getAllDecks(): Flow<Resource<List<Deck>>>
   suspend fun deleteDeck(id: Int): Resource<Unit>
   suspend fun updateDeck(deck: Deck): Resource<Unit>
   suspend fun getPhrasesForNotification(): Resource<List<DeckWithPhrases?>>
}