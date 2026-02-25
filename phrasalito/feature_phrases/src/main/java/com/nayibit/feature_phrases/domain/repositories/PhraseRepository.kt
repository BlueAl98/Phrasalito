package com.nayibit.feature_phrases.domain.repositories

import com.nayibit.feature_phrases.domain.model.Phrase
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface PhraseRepository {
    suspend fun insert(item: Phrase):Result<Boolean, DatabaseError>
  //  suspend fun insertAll(items: List<Phrase>)
    suspend fun delete(item: Phrase) : Result<Boolean, DatabaseError>
    suspend fun update(item: Phrase) : Result<Boolean, DatabaseError>
    suspend fun getById(id: Int): Phrase?
    suspend fun getAllPhrasesByDeckId(idDeck: Int): Flow<Result<List<Phrase>, DatabaseError>>
  /*  suspend fun getRandomPhrase(): Phrase?
    suspend fun getPhrasesToNotify(): List<Phrase>
    suspend fun updateIsNotifiedById(id: Int)
    suspend fun getAllPhrases(): List<Phrase>
    suspend fun resetAllPhrasesToNotify()*/
}