package com.nayibit.feature_languages.domain.model

data class Language(
    val id: Int,
    val code: String,
    val name: String,
    val flag: String,
    val status: Boolean,
    val isDownload: Boolean = false
)
