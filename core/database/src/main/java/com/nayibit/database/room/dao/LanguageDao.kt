package com.nayibit.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nayibit.database.room.entities.LanguageEntity
import com.nayibit.database.room.entities.LanguageWithCategories
import kotlinx.coroutines.flow.Flow

@Dao
interface LanguageDao {

    @Transaction
    @Query("SELECT * FROM languages")
    fun getLanguages(): Flow<List<LanguageWithCategories>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLanguage(language: LanguageEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(languages: List<LanguageEntity>)

    @Delete
    suspend fun deleteLanguage(language: LanguageEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLanguage(language: LanguageEntity)

    @Query("SELECT * FROM languages")
    suspend fun getAll(): List<LanguageEntity>

    @Query("UPDATE languages SET isDownload = 1 WHERE code = :code")
    suspend fun updateIsDownloaded(code: String)
}
