package com.nayibit.feature_categories.data.remote

import com.nayibit.feature_categories.data.remote.dto.LanguageWithCategoriesDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoriesApiService {
    @GET("languages/{languageId}/full")
    suspend fun getCategoriesByLanguage(@Path("languageId") languageId: Int): LanguageWithCategoriesDto
}
