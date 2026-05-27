package com.nayibit.feature_categories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LanguageWithCategoriesDto(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("categories") val categories: List<CategoryDto> = emptyList()
)
