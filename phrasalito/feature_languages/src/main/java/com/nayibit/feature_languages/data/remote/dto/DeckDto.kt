package com.nayibit.feature_languages.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DeckDto(
    @SerializedName("id") val id: Int,
    @SerializedName("categoryId") val categoryId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("maxCards") val maxCards: Int,
    @SerializedName("phrases") val phrases: List<PhraseDto> = emptyList()
)
