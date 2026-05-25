package com.nayibit.feature_languages.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LanguageDto(
    @SerializedName("id") val id: Int,
    @SerializedName("code") val code: String,
    @SerializedName("name") val name: String,
    @SerializedName("flagUrl") val flag: String,
    @SerializedName("available") val status: Boolean
)
