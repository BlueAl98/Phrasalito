package com.nayibit.feature_categories.presentation.categoryScreen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.Flight
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material.icons.filled.Work
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.feature_categories.presentation.model.CategoryUi
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

    init {
        val topics = listOf(
            CategoryUi(1,"Family", "Familia", 0.8f, Icons.Default.AccountTree),
            CategoryUi(2,"Travel", "Viajes", 0.2f, Icons.Default.Flight),
            CategoryUi(3,"Business", "Negocios", 0f, Icons.Default.Work),
            CategoryUi(4,"Daily Life", "Vida Diaria", 0.45f, Icons.Default.WbSunny),
            CategoryUi(5,"Food", "Comida", 0.95f, Icons.Default.Restaurant),
            CategoryUi(6,"Technology", "Tecnologia", 0.1f, Icons.Default.Memory)
        )
        updateState { it.copy(categories = topics) }
    }


    fun onEvent(event: CategoryUiEvent) {
        when (event) {
            is CategoryUiEvent.InsertSkipTutorial -> {
             //  insertInitialConfiguration()
            }

           is CategoryUiEvent.Navigate -> {
                viewModelScope.launch {
                    _eventFlow.emit(CategoryUiEvent.Navigate(event.id))
                }
            }
            is CategoryUiEvent.ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(CategoryUiEvent.ShowToast(event.message))

                }
            }

            CategoryUiEvent.NextPage -> {
            }
        }
     }


    // Helper function to reduce boilerplate
    private fun updateState(block: (CategoryStateUi) -> CategoryStateUi) {
        _state.value = block(_state.value)
    }

}