package com.nayibit.phrasalito_domain.repository

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
   suspend fun insert(deck: Deck): Flow<Resource<Deck>>
   suspend fun getAllDecks(): Flow<Resource<List<Deck>>>
}