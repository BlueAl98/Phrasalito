package com.nayibit.feature_categories.presentation.categoryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.feature_categories.domain.repositories.CategoryRepository
import com.nayibit.feature_categories.model.Category
import com.nayibit.feature_categories.presentation.categoryScreen.CategoryUiEvent.*
import com.nayibit.feature_categories.presentation.mappers.toDomain
import com.nayibit.feature_categories.presentation.mappers.toUI
import com.nayibit.feature_categories.presentation.model.TypeModal
import com.nayibit.utils.helpers.onError
import com.nayibit.utils.helpers.onSuccess
import com.nayibit.utils.helpers.transformAll
import com.nayibit.utils.helpers.update
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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
                when(event.type){
                    TypeModal.CREATE -> updateState { it.copy(showDialog = event.show, typeModal = event.type) }
                    else -> updateState {
                        it.copy(showDialog = event.show,
                            typeModal = event.type,
                            currentCategory = event.category,
                            title = event.category?.title ?: "",
                            subtitle = event.category?.subtitle ?: ""
                            )}
                }
            }

            is OnTextChangeSubtitle -> {
                updateState { it.copy(subtitle = event.subtitle) }
            }
            is OnTextChangeTitle -> {
                updateState { it.copy(title = event.title) }
            }

            is InsertCategory -> {
                insertCategory(Category(name = event.title,
                    subtitle = event.subtitle, languageId = 0))
            }

            is DeleteCategory -> {
                deleteCategory(event.category.toDomain())
            }
            is UpdateCategory -> {
                val category = event.category.copy(title = _state.value.title, subtitle = _state.value.subtitle)
                updateCategory(category.toDomain())
            }

            DissmissDialog -> {
                updateState { state ->
                    state.copy(showDialog = false, currentCategory = null,
                    title = "", subtitle = "", categories = state.categories.transformAll {
                        it.copy(isFlipped = false)
                        }) }
            }

            is FlipCard -> {
                updateState { state ->
                    state.copy(
                        categories = state.categories.update(
                            predicate = { it.id == event.category.id },
                            transform = { it.copy(isFlipped = event.flipped) }
                        )
                    )
                }
            }
        }
     }


    // Helper function to reduce boilerplate
    private fun updateState(block: (CategoryStateUi) -> CategoryStateUi) {
        _state.update { block(it) }
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
    fun updateCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.updateCategory(category).onSuccess {
                updateState { it.copy(showDialog = false, currentCategory = null, title = "", subtitle = "") }
            }.onError { error ->
                println(error)
            }
        }
    }
    fun deleteCategory(category: Category){
        viewModelScope.launch {
            categoryRepository.deleteCategory(category).onSuccess {
                updateState { it.copy(showDialog = false, currentCategory = null, title = "", subtitle = "") }
            }.onError { error ->
                println(error)
            }
        }
    }

    fun getCategories(){
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            categoryRepository.getCategories().collect { result ->
                result.onSuccess { categories ->
                    updateState { it.copy(categories = categories.map { ct-> ct.toUI() }, isLoading = false) }
                }.onError {
                    updateState { it.copy(isLoading = false) } }
                }

            }
        }
    }

