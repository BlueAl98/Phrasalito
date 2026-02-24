package com.nayibit.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nayibit.database.room.entities.CategoryWithDeckEntity
import com.nayibit.database.room.entities.DeckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Transaction
    @Query("SELECT * FROM categories WHERE id = :id")
    fun getAll(id: Int): Flow<CategoryWithDeckEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DeckEntity): Long

    @Delete
    suspend fun deleteDeck(deck: DeckEntity)


    @Update
    suspend fun updateDeck(deck: DeckEntity)

    @Query("SELECT * FROM decks WHERE id = :id")
    suspend fun findDeckById(id: Int): DeckEntity



}