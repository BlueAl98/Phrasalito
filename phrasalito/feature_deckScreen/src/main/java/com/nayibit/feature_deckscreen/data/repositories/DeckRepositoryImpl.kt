package com.nayibit.feature_deckscreen.data.repositories

import com.nayibit.database.room.dao.DeckDao
import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.phrasalito_data.mapper.toDomain
import com.nayibit.phrasalito_data.mapper.toEntity
import com.nayibit.phrasalito_data.mapper.toPhrase
import com.nayibit.phrasalito_data.utils.Constants.INITIAL_DECK
import com.nayibit.phrasalito_data.utils.Constants.INITIAL_PHRASES
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.model.DeckWithPhrases
import com.nayibit.utils.helpers.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeckRepositoryImpl
    @Inject constructor(private val deckDao: DeckDao) : DeckRepository {

        override suspend fun insert(deck: Deck): Flow<Resource<Deck>> = flow {
         try {
             deckDao.insert(deck.toEntity())
             emit(Resource.Success(deck))
         }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
         }
        }

    override fun getAllDecks(): Flow<Resource<List<Deck>>> =
        deckDao.getDecksWithPhrases()
            .map { entities ->
                val decks = entities.map { it.toPhrase() }
                Resource.Success(decks) as Resource<List<Deck>>
            }
            .catch { e ->
                emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
            }



    override suspend fun deleteDeck(id: Int): Resource<Unit> {
        try {
            val deck = deckDao.findDeckById(id)
            deckDao.deleteDeck(deck)
            return Resource.Success(Unit)
        }catch (e : Exception){
            return Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun updateDeck(deck: Deck): Resource<Unit> {
        try {
            deckDao.updateDeck(deck.toEntity())
            return Resource.Success(Unit)
        }catch (e : Exception){
            return Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun getPhrasesForNotification(): Resource<List<DeckWithPhrases?>> {
        try {
           val deckWithPhrases = deckDao.getPhrasesForNotification().map { it?.toDomain() }
            return Resource.Success(deckWithPhrases)
        }catch (e: Exception){
            return Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun createInitialDeck(): Resource<Boolean> {
        try {
            deckDao.deleteAll()
            deckDao.insertDeckWithPhrases(INITIAL_DECK, INITIAL_PHRASES)
            return Resource.Success(true)
        }catch (e: Exception){
            return Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}