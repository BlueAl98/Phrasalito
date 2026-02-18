package com.nayibit.database.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.nayibit.database.room.entities.CategoryWithDeckEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {

    @Query("SELECT * FROM categories")
     fun getCategories(): Flow<List<CategoryWithDeckEntity>>


}