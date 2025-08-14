package com.nayibit.phrasalito_data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nayibit.phrasalito_data.entities.PhraseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insert(item: PhraseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PhraseEntity>)

    @Delete
     suspend fun delete(item: PhraseEntity)

    @Update
     suspend fun update(item: PhraseEntity)

    @Query("SELECT * FROM phrases WHERE id = :id")
     suspend fun getById(id: Int): PhraseEntity?

    @Query("SELECT * FROM phrases")
    fun getAll(): Flow<List<PhraseEntity>>

    @Query("SELECT * FROM phrases WHERE deckId = :idDeck")
    fun getAllByDeckId(idDeck: Int): Flow<List<PhraseEntity>>

}