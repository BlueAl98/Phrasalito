package com.nayibit.feature_categories.data.repositories

import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.entities.CategoryWithDeckEntity
import com.nayibit.feature_categories.data.mappers.toCategory
import com.nayibit.feature_categories.data.mappers.toEntity
import com.nayibit.feature_categories.domain.repositories.CategoryRepository
import com.nayibit.feature_categories.model.Category
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Resource
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao
) : CategoryRepository {

    override suspend fun getCategories(): Flow<Result<List<Category>, DatabaseError>> =
        categoryDao.getCategories()
          .map { entities ->
           val categories = entities.map(CategoryWithDeckEntity::toCategory)
           Result.Success(categories) as Result<List<Category>, DatabaseError>
        }
       .catch { e ->
           emit(Result.Error(DatabaseError.Sql(e)))
       }

    override suspend fun insertCategory(category: Category): Result<Unit, DatabaseError> {
       try {
           categoryDao.insertCategory(category.toEntity())
           return Result.Success(Unit)
       }catch (e: Exception){
           return Result.Error(DatabaseError.Sql(e))
       }
    }

    override suspend fun updateCategory(category: Category): Result<Unit, DatabaseError> {
        try {
            val findCategory = categoryDao.getCategoryById(category.id).category
            val catUpdate = findCategory.copy(name = category.name, subtitle = category.subtitle)
            categoryDao.updateCategory(catUpdate)
            return Result.Success(Unit)
        }catch (e: Exception){
            return Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun deleteCategory(category: Category): Result<Unit, DatabaseError> {
        try {
            val category = categoryDao.getCategoryById(category.id).category
            categoryDao.deleteCategory(category)
            return Result.Success(Unit)
        }catch (e: Exception){
            return Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun getCategoryById(id: Int): Result<Category, DatabaseError> {
        try {
            val category = categoryDao.getCategoryById(id)
            return Result.Success(category.toCategory())
        }catch (e: Exception){
            return Result.Error(DatabaseError.Sql(e))
        }
    }


}