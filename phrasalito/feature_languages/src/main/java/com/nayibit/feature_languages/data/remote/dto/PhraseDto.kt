package com.nayibit.feature_languages.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PhraseDto(
    @SerializedName("id") val id: Int,
    @SerializedName("targetLanguage") val targetLanguage: String,
    @SerializedName("translation") val translation: String,
    @SerializedName("example") val example: String
)
