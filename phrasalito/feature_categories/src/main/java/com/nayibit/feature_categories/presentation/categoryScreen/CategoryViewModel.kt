package com.nayibit.feature_categories.presentation.categoryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.feature_categories.domain.repositories.CategoryRepository
import com.nayibit.feature_categories.model.Category
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.InsertSkipTutorial
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.Navigate
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.NextPage
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.OnTextChangeSubtitle
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.OnTextChangeTitle
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.ShowDialog
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.ShowToast
import com.nayibit.feature_categories.presentation.mappers.toUI
import com.nayibit.utils.helpers.onError
import com.nayibit.utils.helpers.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
): ViewModel() {

    private val _state = MutableStateFlow(CategoryStateUi())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CategoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
       getCategories()
    }


    fun onEvent(event: CategoryUiEvent) {
        when (event) {
            is InsertSkipTutorial -> {
             //  insertInitialConfiguration()
            }

           is Navigate -> {
                viewModelScope.launch {
                    _eventFlow.emit(Navigate(event.id))
                }
            }
            is ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(ShowToast(event.message))

                }
            }

            NextPage -> {
            }

            is ShowDialog -> {
                updateState { it.copy(showDialog = event.show) }
            }

            is OnTextChangeSubtitle -> {
                updateState { it.copy(subtitle = event.subtitle) }
            }
            is OnTextChangeTitle -> {
                updateState { it.copy(title = event.title) }
            }

            is CategoryUiEvent.InsertCategory -> {
                insertCategory(Category(name = event.title, subtitle = event.subtitle))
            }
        }
     }


    // Helper function to reduce boilerplate
    private fun updateState(block: (CategoryStateUi) -> CategoryStateUi) {
        _state.value = block(_state.value)
    }


    fun insertCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.insertCategory(category).onSuccess {
                updateState { it.copy(showDialog = false) }
            }.onError { error ->
                println(error)
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            categoryRepository.getCategories().collect { result ->
                result.onSuccess { categories ->
                    updateState { it.copy(categories = categories.map { ct-> ct.toUI() }) }
                }.onError {
                    println(it)
                }

            }
        }
    }

}