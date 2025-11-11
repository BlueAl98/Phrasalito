package com.nayibit.phrasalito_data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.entities.PhraseEntity
import com.nayibit.phrasalito_data.model.DeckWithPhrasesDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {


 @Transaction
 suspend fun insertDeckWithPhrases(deck: DeckEntity, phrases: List<PhraseEntity>) {
      if (!existsDeckInDB(1)){
          insert(deck)
          insertPhrases(phrases)
      }
 }

    @Query("SELECT EXISTS(SELECT 1 FROM decks WHERE id = :id)")
    suspend fun existsDeckInDB(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhrases(phrases: List<PhraseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DeckEntity): Long

    @Query("SELECT * FROM decks")
     fun getAll(): Flow<List<DeckEntity>>

     @Transaction
     @Query("SELECT * FROM decks")
      fun getDecksWithPhrases(): Flow<List<DeckWithPhrasesDto>>

      @Transaction
      @Query("SELECT * FROM decks WHERE isNotified = 1")
       fun getPhrasesForNotification(): List<DeckWithPhrasesDto?>

     @Delete
     suspend fun deleteDeck(deck: DeckEntity)

     @Query("DELETE FROM decks")
     suspend fun deleteAll()

      @Update
     suspend fun updateDeck(deck: DeckEntity)

      @Query("SELECT * FROM decks WHERE id = :id")
      suspend fun findDeckById(id: Int): DeckEntity

}