package com.nayibit.feature_categories.presentation.categoryScreen

import com.nayibit.feature_categories.presentation.model.CategoryUi
import com.nayibit.feature_categories.presentation.model.TypeModal

sealed interface CategoryUiEvent {
    data class Navigate(val id: Int): CategoryUiEvent
    object InsertSkipTutorial: CategoryUiEvent
    data class ShowToast(val message: String) : CategoryUiEvent
    object NextPage: CategoryUiEvent
    data class ShowDialog(val show: Boolean, val type: TypeModal = TypeModal.CREATE, val category: CategoryUi? = null): CategoryUiEvent
    data class OnTextChangeTitle(val title: String): CategoryUiEvent
    data class OnTextChangeSubtitle(val subtitle: String): CategoryUiEvent
    data class OnIconChange(val icon: String): CategoryUiEvent
    data class InsertCategory(val title: String, val subtitle: String): CategoryUiEvent
    data class UpdateCategory(val category: CategoryUi): CategoryUiEvent
    data class DeleteCategory(val category: CategoryUi): CategoryUiEvent
    data class FlipCard(val category: CategoryUi ,val flipped: Boolean): CategoryUiEvent
    object DissmissDialog: CategoryUiEvent
    object ChangeLanguage: CategoryUiEvent
}
