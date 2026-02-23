package com.nayibit.feature_categories.presentation.categoryScreen

sealed interface CategoryUiEvent {
    data class Navigate(val id: Int): CategoryUiEvent
    object InsertSkipTutorial: CategoryUiEvent
    data class ShowToast(val message: String) : CategoryUiEvent
    object NextPage: CategoryUiEvent
    data class ShowDialog(val show: Boolean): CategoryUiEvent
    data class OnTextChangeTitle(val title: String): CategoryUiEvent
    data class OnTextChangeSubtitle(val subtitle: String): CategoryUiEvent
    data class InsertCategory(val title: String, val subtitle: String): CategoryUiEvent
}
