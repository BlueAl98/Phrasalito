package com.ie.feature_startscreen.data.repositories

import com.ie.feature_startscreen.data.mappers.toPhrase
import com.ie.feature_startscreen.domain.model.Phrase
import com.ie.feature_startscreen.domain.repositories.CategoryRepository
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import com.nayibit.datastore.utils.getData
import com.nayibit.utils.DataStoreKeys.SELECTED_LANGUAGE_ID
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dao: CategoryDao,
    private val dataStore: DataStoreRepository
) : CategoryRepository {

    override suspend fun getCategoriesByLanguage(): Result<List<Phrase>> {
        val idLanguage = dataStore.getData(SELECTED_LANGUAGE_ID, 0).first()
        if (idLanguage == 0) return Result.success(emptyList())
        val phrases = dao.getCategoriesByLanguage(idLanguage).first()
            .flatMap { category ->
                category.decks
                    .filter { it.deck.isNotified }
                    .flatMap { deck -> deck.phrases.map { it.toPhrase() } }
            }
        return Result.success(phrases)
    }
}