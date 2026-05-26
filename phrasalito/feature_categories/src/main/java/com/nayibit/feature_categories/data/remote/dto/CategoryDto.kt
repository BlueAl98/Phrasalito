package com.nayibit.feature_categories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("id") val id: Int,
    @SerializedName("languageId") val languageId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("subtitle") val subtitle: String,
    @SerializedName("icon") val icon: String,
    @SerializedName("numDecks") val numDecks: Int
)
