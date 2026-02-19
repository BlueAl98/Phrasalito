package com.nayibit.feature_categories.presentation.categoryScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(CategoryStateUi())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<CategoryUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()



    fun onEvent(event: CategoryUiEvent) {
        when (event) {
            is CategoryUiEvent.InsertSkipTutorial -> {
             //  insertInitialConfiguration()
            }

           is CategoryUiEvent.Navigate -> {
                viewModelScope.launch {
                    _eventFlow.emit(CategoryUiEvent.Navigate)
                }
            }
            is CategoryUiEvent.ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(CategoryUiEvent.ShowToast(event.message))

                }
            }

            CategoryUiEvent.NextPage -> {
              /*  _state.value = _state.value.copy(
                    currentPage = _state.value.currentPage + 1
                )*/
                viewModelScope.launch {
                    _eventFlow.emit(CategoryUiEvent.Navigate)
                }
            }
        }
     }


    // Helper function to reduce boilerplate
    private fun updateState(block: (CategoryStateUi) -> CategoryStateUi) {
        _state.value = block(_state.value)
    }

}