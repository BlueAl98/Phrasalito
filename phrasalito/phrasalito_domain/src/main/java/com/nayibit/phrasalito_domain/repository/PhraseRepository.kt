package com.nayibit.phrasalito_domain.repository

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Phrase
import kotlinx.coroutines.flow.Flow

interface PhraseRepository {
    suspend fun insert(item: Phrase):Flow<Resource<Boolean>>
  //  suspend fun insertAll(items: List<Phrase>)
    suspend fun delete(item: Phrase) : Flow<Resource<Boolean>>
    suspend fun update(item: Phrase) : Flow<Resource<Boolean>>
    suspend fun getById(id: Int): Phrase?
    suspend fun getAllPhrasesByDeckId(idDeck: Int): Flow<Resource<List<Phrase>>>
    suspend fun getRandomPhrase(): Phrase?
    suspend fun getPhrasesToNotify(): List<Phrase>
    suspend fun updateIsNotifiedById(id: Int)
    suspend fun getAllPhrases(): List<Phrase>
    suspend fun resetAllPhrasesToNotify()
}