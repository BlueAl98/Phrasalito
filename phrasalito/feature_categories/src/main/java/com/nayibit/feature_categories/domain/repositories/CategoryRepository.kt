package com.nayibit.feature_categories.domain.repositories

import com.nayibit.database.utils.DatabaseError
import com.nayibit.feature_categories.model.Category
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    fun getCategories(languageId: Int): Flow<Result<List<Category>, DatabaseError>>
    suspend fun fetchAndSeedIfNeeded(languageId: Int): Result<Unit, DatabaseError>
    suspend fun insertCategory(category: Category): Result<Unit, DatabaseError>
    suspend fun updateCategory(category: Category): Result<Unit, DatabaseError>
    suspend fun deleteCategory(category: Category): Result<Unit, DatabaseError>
    suspend fun getCategoryById(id: Int): Result<Category, DatabaseError>
}
