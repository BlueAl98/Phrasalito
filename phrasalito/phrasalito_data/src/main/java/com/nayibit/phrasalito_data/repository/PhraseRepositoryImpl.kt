package com.nayibit.phrasalito_data.repository

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_data.dao.PhraseDao
import com.nayibit.phrasalito_data.mapper.toDomain
import com.nayibit.phrasalito_data.mapper.toEntity
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhraseRepositoryImpl @Inject
  constructor( private val phraseDao: PhraseDao): PhraseRepository {

    override suspend fun insert(item: Phrase): Flow<Resource<Boolean>> = flow {
       try {
           emit(Resource.Loading)
           delay(2000)
           phraseDao.insert(item.toEntity())
           emit(Resource.Success(true))

       }catch (e: Exception){
           emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
       }
    }

    override suspend fun delete(item: Phrase) = flow {
        emit(Resource.Loading)
        delay(1000)
        try {

            phraseDao.delete(item.toEntity())
            emit(Resource.Success(true))
        }catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }

    }

    override suspend fun update(item: Phrase): Flow<Resource<Boolean>> = flow {
        try {
            emit(Resource.Loading)
            phraseDao.update(item.toEntity())
            emit(Resource.Success(true))
        }catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))
        }
    }

    override suspend fun getById(id: Int): Phrase? {
        return phraseDao.getById(id)?.toDomain()
    }

    override suspend fun getAllPhrasesByDeckId(idDeck: Int): Flow<Resource<List<Phrase>>> = flow {
        emit(Resource.Loading)
        delay(2000)
        try {
            phraseDao.getAllByDeckId(idDeck)
                .collect { entities ->
                    val phrases = entities.map { it.toDomain() }
                    emit(Resource.Success(phrases))
                }

        }catch (e: Exception){
            emit(Resource.Error(e.localizedMessage ?: "Unknown error"))}
    }

    override suspend fun getRandomPhrase(): Phrase? {
        return phraseDao.getAll().map { it.toDomain() }.randomOrNull()
    }
}