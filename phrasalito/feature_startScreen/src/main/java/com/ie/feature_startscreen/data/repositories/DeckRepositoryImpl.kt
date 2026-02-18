package com.ie.feature_startscreen.data.repositories

import com.ie.feature_startscreen.domain.repositories.DeckRepository
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.database.room.dao.DeckDao
import javax.inject.Inject

class DeckRepositoryImpl @Inject constructor(
    private val dao: CategoryDao
) : DeckRepository {

    override suspend fun insetDeck() {
         dao.getCategories().collect {
             println(it)
         }

    }
}