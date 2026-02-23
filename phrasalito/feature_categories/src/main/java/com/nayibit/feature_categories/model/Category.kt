package com.nayibit.feature_categories.model

data class Category(
    val id: Int = 0,
    val name: String,
    val subtitle: String = "",
    val maxDecks: Int = 20,
    val currentDecks: Int = 0,
    val progress: Float = 0f,
)
