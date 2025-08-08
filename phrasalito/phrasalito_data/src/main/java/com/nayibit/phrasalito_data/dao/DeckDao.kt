package com.nayibit.phrasalito_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nayibit.phrasalito_data.entities.DeckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DeckEntity)

    @Query("SELECT * FROM decks")
     fun getAll(): Flow<List<DeckEntity>>


}