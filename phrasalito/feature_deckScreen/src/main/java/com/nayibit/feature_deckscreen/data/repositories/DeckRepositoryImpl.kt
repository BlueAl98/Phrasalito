package com.nayibit.feature_deckscreen.data.repositories

import com.nayibit.database.room.dao.DeckDao
import com.nayibit.feature_deckscreen.data.mappers.toDeck
import com.nayibit.feature_deckscreen.data.mappers.toEntity
import com.nayibit.feature_deckscreen.domain.model.Deck
import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeckRepositoryImpl
    @Inject constructor(private val deckDao: DeckDao) : DeckRepository {

    override suspend fun insert(deck: Deck): Result<Deck, DatabaseError> {
       return try {
             deckDao.insert(deck.toEntity())
              Result.Success(deck)
         }catch (e: Exception){
            Result.Error(DatabaseError.Sql(e))
         }
      }


    override fun getAllDecks(id: Int): Flow<Result<List<Deck>, DatabaseError>> =
        deckDao.getAll(id)
            .map { entity ->
                val decks = entity.decks.map { it.deck.toDeck(it.phrases.size)}
                Result.Success(decks) as Result<List<Deck>, DatabaseError>
            }
            .catch { e ->
                emit(Result.Error(DatabaseError.Sql(e)))
            }



    override suspend fun deleteDeck(id: Int): Result<Unit, DatabaseError> {
        return try {
            val deck = deckDao.findDeckById(id)
            deckDao.deleteDeck(deck)
            Result.Success(Unit)
        }catch (e : Exception){
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun updateDeck(deck: Deck): Result<Unit, DatabaseError> {
        return try {
            deckDao.updateDeck(deck.toEntity())
            Result.Success(Unit)
        }catch (e : Exception){
            Result.Error(DatabaseError.Sql(e))
        }
    }

}