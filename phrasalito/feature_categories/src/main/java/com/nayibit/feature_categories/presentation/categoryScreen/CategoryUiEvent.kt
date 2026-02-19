package com.nayibit.feature_categories.presentation.categoryScreen

sealed class CategoryUiEvent {
    object Navigate: CategoryUiEvent()
    object InsertSkipTutorial: CategoryUiEvent()
    data class ShowToast(val message: String) : CategoryUiEvent()
    object NextPage: CategoryUiEvent()

}