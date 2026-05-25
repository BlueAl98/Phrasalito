package com.nayibit.feature_languages.data.remote

import com.nayibit.feature_languages.data.remote.dto.LanguageDto
import retrofit2.http.GET
import retrofit2.http.Path

interface LanguagesApiService {
    @GET("languages/{id}")
    suspend fun getLanguage(@Path("id") id: Int): LanguageDto
}
