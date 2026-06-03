package com.nayibit.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.nayibit.database.room.entities.PhraseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhraseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: PhraseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<PhraseEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(items: List<PhraseEntity>)

    @Query("DELETE FROM phrases WHERE id = :id")
    suspend fun delete(id: Int)

    @Update
    suspend fun update(item: PhraseEntity)

    @Query("SELECT * FROM phrases WHERE id = :id")
    suspend fun getById(id: Int): PhraseEntity?

    @Query("SELECT * FROM phrases")
    fun getAll():   List<PhraseEntity>

    @Query("SELECT * FROM phrases WHERE deckId = :idDeck")
    fun getAllByDeckId(idDeck: Int): Flow<List<PhraseEntity>>

    @Query("SELECT * FROM phrases WHERE isNotified = 0")
    suspend fun getPhrasesToNotify(): List<PhraseEntity>

    // Update only rows that match a condition
    @Query("UPDATE phrases SET isNotified = 1 WHERE id = :id")
    suspend fun updateIsNotifiedById(id: Int)

    @Query("UPDATE phrases SET isNotified = 0")
    suspend fun updateAllPhrasesToNofity()

}