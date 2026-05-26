package com.ie.feature_startscreen.presentation.startScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ie.feature_startscreen.domain.usecases.GetFirstTimeUseCase
import com.ie.feature_startscreen.domain.usecases.InsertFirstTimeUseCase
import com.nayibit.utils.helpers.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val insertFirstTimeUseCase: InsertFirstTimeUseCase,
    private val getFirstTimeUseCase: GetFirstTimeUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(StartStateUi())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<StartUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

   init {
       getFirstTime()
   }


    fun onEvent(event: StartUiEvent) {
        when (event) {
            is StartUiEvent.InsertSkipTutorial -> {
              insertFirstTime()
            }

           is StartUiEvent.Navigate -> {
                viewModelScope.launch {
                    _eventFlow.emit(StartUiEvent.Navigate)
                }
            }
            is StartUiEvent.ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(StartUiEvent.ShowToast(event.message))

                }
            }

            StartUiEvent.NextPage -> {
                _state.value = _state.value.copy(
                    currentPage = _state.value.currentPage + 1
                )
            }
        }
     }

    fun getFirstTime() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }
            getFirstTimeUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            updateState { it.copy(isLoading = false, errorMessage = result.message) }
                        }
                        is Resource.Success -> {
                            updateState { it.copy(isFirstTime = result.data, isLoading = false) }
                            if (result.data) {
                                _eventFlow.emit(StartUiEvent.Navigate)
                            }
                        }
                    }
                }
        }
    }

    // Helper function to reduce boilerplate
    private fun updateState(block: (StartStateUi) -> StartStateUi) {
        _state.value = block(_state.value)
    }



        fun insertFirstTime() {
            viewModelScope.launch {
                _state.value = _state.value.copy(
                    isLoading = true
                )
                insertFirstTimeUseCase().collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                            _eventFlow.emit(StartUiEvent.ShowToast("Error: ${result.message}"))
                        }

                        is Resource.Success<*> -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                checkPermissions = true
                            )
                            _eventFlow.emit(StartUiEvent.Navigate)
                        }
                    }
                }

            }
        }

    }