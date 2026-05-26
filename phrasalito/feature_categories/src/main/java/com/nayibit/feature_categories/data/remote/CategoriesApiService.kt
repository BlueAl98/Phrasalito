package com.nayibit.feature_categories.data.remote

import com.nayibit.feature_categories.data.remote.dto.CategoryDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApiService {
    @GET("languages/{languageId}/categories")
    suspend fun getCategoriesByLanguage(@Path("languageId") languageId: Int): List<CategoryDto>
}
