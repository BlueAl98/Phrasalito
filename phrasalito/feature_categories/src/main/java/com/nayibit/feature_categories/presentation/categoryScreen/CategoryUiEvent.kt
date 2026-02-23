package com.nayibit.feature_categories.presentation.categoryScreen

sealed class CategoryUiEvent {
    data class Navigate(val id: Int): CategoryUiEvent()
    object InsertSkipTutorial: CategoryUiEvent()
    data class ShowToast(val message: String) : CategoryUiEvent()
    object NextPage: CategoryUiEvent()
    data class ShowDialog(val show: Boolean): CategoryUiEvent()

}