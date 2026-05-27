package com.nayibit.feature_categories.presentation.categoryScreen

import com.nayibit.feature_categories.presentation.model.CategoryUi
import com.nayibit.feature_categories.presentation.model.TypeModal


data class CategoryStateUi(
    val isLoading: Boolean = false,
    val categories: List<CategoryUi> = emptyList(),
    val showDialog: Boolean = false,
    val title: String = "",
    val subtitle: String = "",
    val selectedIcon: String = "",
    val currentCategory: CategoryUi? = null,
    val typeModal: TypeModal = TypeModal.CREATE)
