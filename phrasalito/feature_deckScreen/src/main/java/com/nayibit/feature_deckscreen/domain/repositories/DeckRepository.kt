package com.nayibit.feature_deckscreen.domain.repositories


import com.nayibit.feature_deckscreen.domain.model.Deck
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface DeckRepository {
   suspend fun insert(deck: Deck):Result<Deck, DatabaseError>
   suspend fun deleteDeck(id: Int): Result<Unit,DatabaseError>
   suspend fun updateDeck(deck: Deck): Result<Unit,DatabaseError>
    fun getAllDecks(id: Int): Flow<Result<List<Deck>, DatabaseError>>
}