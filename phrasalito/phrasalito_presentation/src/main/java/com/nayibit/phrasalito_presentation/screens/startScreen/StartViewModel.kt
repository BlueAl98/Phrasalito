package com.nayibit.phrasalito_presentation.screens.startScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.useCases.dataStore.GetFirstTimeUseCase
import com.nayibit.phrasalito_domain.useCases.dataStore.InsertFirstTimeUseCase
import com.nayibit.phrasalito_presentation.screens.startScreen.StartUiEvent.InsertSkipTutorial
import com.nayibit.phrasalito_presentation.screens.startScreen.StartUiEvent.Navigate
import com.nayibit.phrasalito_presentation.screens.startScreen.StartUiEvent.NextPage
import com.nayibit.phrasalito_presentation.screens.startScreen.StartUiEvent.ShowToast
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
    private val getFirstTimeUseCase: GetFirstTimeUseCase
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
            is InsertSkipTutorial -> {
                insertFirstTime()
            }

           is Navigate -> {
                viewModelScope.launch {
                    _eventFlow.emit(Navigate)
                }
            }
            is ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(ShowToast(event.message))

                }
            }

            NextPage -> {
                _state.value = _state.value.copy(
                    currentPage = _state.value.currentPage + 1
                )
            }
        }
     }





    fun getFirstTime() {
        viewModelScope.launch {
            getFirstTimeUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Error -> {
                            updateState { it.copy(isLoading = false, errorMessage = result.message) }
                        }
                        Resource.Loading -> {
                            updateState { it.copy(isLoading = true) }
                        }
                        is Resource.Success -> {
                            updateState { it.copy(isFirstTime = result.data, isLoading = false) }
                            if (result.data) {
                                viewModelScope.launch { _eventFlow.emit(Navigate) }
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
                insertFirstTimeUseCase().collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true
                            )
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                            _eventFlow.emit(ShowToast("Error: ${result.message}"))

                        }

                        is Resource.Success<*> -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                checkPermissions = true
                            )
                            _eventFlow.emit(Navigate)
                        }
                    }
                }

            }
        }

    }