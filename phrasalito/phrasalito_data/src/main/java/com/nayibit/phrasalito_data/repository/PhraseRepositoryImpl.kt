package com.nayibit.phrasalito_data.repository

import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.entities.PhraseEntity
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import javax.inject.Inject

class PhraseRepositoryImpl @Inject
  constructor( private val phraseDao: PhraseDao): PhraseRepository {

    override suspend fun insert(item: Phrase) {
        val phraseEntity = PhraseEntity(
            id = item.id,
            targetLanguage = item.targetLanguage,
            translation = item.translation
        )
        phraseDao.insert(phraseEntity)
    }

    override suspend fun getAll(): List<Phrase> {
       return phraseDao.getAll().map { it.toDomain() }
    }


}