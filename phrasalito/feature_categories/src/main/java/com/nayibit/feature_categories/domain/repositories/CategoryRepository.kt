package com.nayibit.feature_categories.domain.repositories

import com.nayibit.feature_categories.model.Category
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getCategories() : Flow<Result<List<Category>, DatabaseError >>
    suspend fun insertCategory(category: Category): Result<Unit, DatabaseError >

}