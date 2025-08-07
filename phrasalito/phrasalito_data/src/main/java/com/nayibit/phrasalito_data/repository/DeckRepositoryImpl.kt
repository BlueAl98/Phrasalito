package com.nayibit.phrasalito_data.repository

import com.nayibit.phrasalito_data.dao.DeckDao
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.repository.DeckRepository
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeckRepositoryImpl
    @Inject constructor(private val deckDao: DeckDao) : DeckRepository  {

        override suspend fun insert(deck: Deck): Flow<Resource<Deck>> = flow {
            emit(Resource.Loading)
         try {
             val deckEntity = DeckEntity(
                 name = deck.name,
                 maxCards = deck.maxCards
             )
             deckDao.insert(deckEntity)
             emit(Resource.Success(deck))
         }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
         }
        }
}