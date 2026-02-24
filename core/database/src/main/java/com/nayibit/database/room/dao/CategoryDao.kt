package com.nayibit.database.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.nayibit.database.room.entities.CategoryEntity
import com.nayibit.database.room.entities.CategoryWithDeckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

     @Transaction
     @Query("SELECT * FROM categories")
     fun getCategories(): Flow<List<CategoryWithDeckEntity>>

     @Insert(onConflict = OnConflictStrategy.REPLACE)
     suspend fun insertCategory(category: CategoryEntity)

     @Delete
     suspend fun deleteCategory(category: CategoryEntity)

     @Update(onConflict = OnConflictStrategy.REPLACE)
     suspend fun updateCategory(category: CategoryEntity)

     @Query("SELECT * FROM categories WHERE id = :id")
     suspend fun getCategoryById(id: Int): CategoryWithDeckEntity



}