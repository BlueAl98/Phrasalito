package com.nayibit.phrasalito_data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.model.DeckWithPhrasesDto
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DeckEntity)

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

      @Update
     suspend fun updateDeck(deck: DeckEntity)

      @Query("SELECT * FROM decks WHERE id = :id")
      suspend fun findDeckById(id: Int): DeckEntity

}