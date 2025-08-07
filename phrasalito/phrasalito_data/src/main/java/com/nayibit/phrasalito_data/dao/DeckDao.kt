package com.nayibit.phrasalito_data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.nayibit.phrasalito_data.entities.DeckEntity

@Dao
interface DeckDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: DeckEntity)

}