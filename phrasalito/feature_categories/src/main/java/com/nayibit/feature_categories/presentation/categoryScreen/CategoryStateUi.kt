package com.nayibit.feature_categories.presentation.categoryScreen

import com.nayibit.feature_categories.presentation.model.CategoryUi


data class CategoryStateUi(
    val isLoading: Boolean = false,
    val categories: List<CategoryUi> = emptyList(),
    val showDialog: Boolean = false
)
