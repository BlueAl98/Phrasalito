package com.nayibit.feature_categories.data.repositories

import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import com.nayibit.database.room.dao.PhraseDao
import com.nayibit.database.room.entities.CategoryWithDeckEntity
import com.nayibit.database.utils.DatabaseError
import com.nayibit.feature_categories.data.mappers.toCategory
import com.nayibit.feature_categories.data.mappers.toEntity
import com.nayibit.feature_categories.data.remote.CategoriesApiService
import com.nayibit.feature_categories.data.remote.mapper.toEntity
import com.nayibit.feature_categories.domain.repositories.CategoryRepository
import com.nayibit.feature_categories.model.Category
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val deckDao: DeckDao,
    private val phraseDao: PhraseDao,
    private val apiService: CategoriesApiService
) : CategoryRepository {

    override fun getCategories(languageId: Int): Flow<Result<List<Category>, DatabaseError>> =
        categoryDao.getCategoriesByLanguage(languageId)
            .map { entities ->
                val categories = entities.map(CategoryWithDeckEntity::toCategory)
                Result.Success(categories) as Result<List<Category>, DatabaseError>
            }
            .catch { e ->
                println(e)
                emit(Result.Error(DatabaseError.Sql(e)))
            }

    override suspend fun fetchAndSeedIfNeeded(languageId: Int): Result<Unit, DatabaseError> {
        if (categoryDao.hasDefaultCategories(languageId)) return Result.Success(Unit)
        return try {
            val response = apiService.getCategoriesByLanguage(languageId)

            val categoryEntities = response.categories.map { it.toEntity(languageId) }
            categoryDao.insertCategories(categoryEntities)

            val deckEntities = response.categories.flatMap { category ->
                category.decks.map { it.toEntity(lngCode = response.code, languageName = response.name) }
            }
            if (deckEntities.isNotEmpty()) {
                deckDao.insertAll(deckEntities)
            }

            val phraseEntities = response.categories.flatMap { category ->
                category.decks.flatMap { deck ->
                    deck.phrases.map { it.toEntity(deckId = deck.id) }
                }
            }
            if (phraseEntities.isNotEmpty()) {
                phraseDao.insertAll(phraseEntities)
            }

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DatabaseError.Unknown)
        }
    }

    override suspend fun insertCategory(category: Category): Result<Unit, DatabaseError> {
        return try {
            categoryDao.insertCategory(category.toEntity())
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun updateCategory(category: Category): Result<Unit, DatabaseError> {
        return try {
            val existing = categoryDao.getCategoryById(category.id).category
            categoryDao.updateCategory(existing.copy(name = category.name, subtitle = category.subtitle, icon = category.icon))
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun deleteCategory(category: Category): Result<Unit, DatabaseError> {
        return try {
            val entity = categoryDao.getCategoryById(category.id).category
            if (entity.isDefault) return Result.Error(DatabaseError.ProtectedCategory)
            categoryDao.deleteCategory(entity)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(DatabaseError.Sql(e))
        }
    }

    override suspend fun getCategoryById(id: Int): Result<Category, DatabaseError> {
        return try {
            Result.Success(categoryDao.getCategoryById(id).toCategory())
        } catch (e: Exception) {
            Result.Error(DatabaseError.Sql(e))
        }
    }
}
