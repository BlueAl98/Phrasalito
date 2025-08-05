package com.nayibit.phrasalito_data.repository

import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.entities.PhraseEntity
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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

    override suspend fun getAll(): Flow<Resource<List<Phrase>>> = flow {
        emit(Resource.Loading)
        delay(2000)
        try {
            phraseDao.getAll()
                .collect { entities ->
                    val phrases = entities.map { it.toDomain() }
                    emit(Resource.Success(phrases))
                }

        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))}
    }
 }