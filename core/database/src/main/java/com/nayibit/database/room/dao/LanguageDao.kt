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

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllIgnore(languages: List<LanguageEntity>)

    @Query("UPDATE languages SET name = :name, flag = :flag, status = :status WHERE id = :id")
    suspend fun updateMetadata(id: Int, name: String, flag: String, status: Boolean)

    @Delete
    suspend fun deleteLanguage(language: LanguageEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateLanguage(language: LanguageEntity)

    @Query("SELECT * FROM languages")
     fun getAll(): Flow<List<LanguageEntity>>

    @Query("UPDATE languages SET isDownload = 1 WHERE code = :code")
    suspend fun updateIsDownloaded(code: String)
}
