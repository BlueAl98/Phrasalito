package com.nayibit.phrasalito_domain.repository

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
   suspend fun insert(deck: Deck): Flow<Resource<Deck>>
}