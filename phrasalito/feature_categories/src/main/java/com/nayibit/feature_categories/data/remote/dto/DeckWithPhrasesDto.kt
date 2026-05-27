package com.nayibit.feature_categories.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DeckWithPhrasesDto(
    @SerializedName("id") val id: Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("maxCards") val maxCards: Int,
    @SerializedName("phrases") val phrases: List<PhraseDto> = emptyList()
)
