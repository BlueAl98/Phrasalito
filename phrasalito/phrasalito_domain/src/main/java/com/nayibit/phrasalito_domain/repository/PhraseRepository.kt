package com.nayibit.phrasalito_domain.repository

import com.nayibit.phrasalito_domain.model.Phrase
import kotlinx.coroutines.flow.Flow

interface PhraseRepository {
    suspend fun insert(item: Phrase)
  //  suspend fun insertAll(items: List<Phrase>)
   // suspend fun delete(item: Phrase)
   // suspend fun update(item: Phrase)
  //  suspend fun getById(id: Int): Phrase?
    suspend fun getAll(): Flow<List<Phrase>>
}