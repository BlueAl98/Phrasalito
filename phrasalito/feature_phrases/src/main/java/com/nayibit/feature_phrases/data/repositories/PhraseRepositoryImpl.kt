package com.nayibit.feature_phrases.data.repositories


import com.nayibit.database.room.dao.PhraseDao
import com.nayibit.feature_phrases.data.mapper.toEntity
import com.nayibit.feature_phrases.data.mapper.toPhrase
import com.nayibit.feature_phrases.domain.model.Phrase
import com.nayibit.feature_phrases.domain.repositories.PhraseRepository
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PhraseRepositoryImpl @Inject
  constructor( private val phraseDao: PhraseDao): PhraseRepository {

    override suspend fun insert(item: Phrase): Result<Boolean, DatabaseError> {
        return try {
            phraseDao.insert(item.toEntity())
            Result.Success(true)
        }catch (e: Exception){
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun delete(item: Phrase): Result<Boolean, DatabaseError> {
        return try {
            phraseDao.delete(item.toEntity())
            Result.Success(true)
        }catch (e : Exception){
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun update(item: Phrase): Result<Boolean, DatabaseError> {
        return try {
            phraseDao.update(item.toEntity())
            Result.Success(true)
        }catch (e : Exception){
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun getById(id: Int): Phrase? {
        return try {
            phraseDao.getById(id)?.toPhrase()
        }catch (e : Exception){
            null
        }
    }

    override suspend fun getAllPhrasesByDeckId(idDeck: Int): Flow<Result<List<Phrase>, DatabaseError>> =
        phraseDao.getAllByDeckId(idDeck)
            .map { entity ->
                val phrases = entity.map { it.toPhrase() }
                Result.Success(phrases) as Result<List<Phrase>, DatabaseError>
            }
            .catch { e ->
                emit(Result.Error(DatabaseError.Sql(e)))
            }


    /*

   override suspend fun getRandomPhrase(): Phrase? {
       return phraseDao.getAll().map { it.toPhrase() }.randomOrNull()
   }

   override suspend fun getPhrasesToNotify(): List<Phrase> {
       return phraseDao.getPhrasesToNotify().map { it.toPhrase() }
   }

   override suspend fun updateIsNotifiedById(id: Int) {
       phraseDao.updateIsNotifiedById(id)
   }

   override suspend fun getAllPhrases(): List<Phrase> {
       return phraseDao.getAll().map { it.toPhrase() }
   }

   override suspend fun resetAllPhrasesToNotify() {
       phraseDao.updateAllPhrasesToNofity()
   }*/

}