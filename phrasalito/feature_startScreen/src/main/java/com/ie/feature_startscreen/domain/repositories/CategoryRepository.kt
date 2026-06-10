package com.ie.feature_startscreen.domain.repositories

import com.ie.feature_startscreen.domain.model.Phrase
import com.nayibit.database.room.entities.CategoryWithDeckEntity

interface CategoryRepository {
    suspend fun getCategoriesByLanguage(): Result<List<Phrase>>
}