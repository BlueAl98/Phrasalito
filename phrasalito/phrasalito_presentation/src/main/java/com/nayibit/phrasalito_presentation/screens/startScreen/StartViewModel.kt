package com.nayibit.phrasalito_presentation.screens.startScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.useCases.dataStore.InsertSkipTutorialUseCase
import com.nayibit.phrasalito_presentation.screens.startScreen.StartUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val insertSkipTutorialUseCase: InsertSkipTutorialUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(StartStateUi())
    val state = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<StartUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onEvent(event: StartUiEvent) {
        when (event) {
            is InsertSkipTutorial -> {
                skipTutorial()
            }

            Navigate -> {
                viewModelScope.launch {
                    _eventFlow.emit(Navigate)
                }
            }
            is ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(ShowToast(event.message))

                }
            }

            is SetHasPermission -> {
                _state.value = _state.value.copy(
                    hasPermission = event.hasPermission
                )
                if (event.hasPermission){
                    skipTutorial()
                }
            }

            NextPage -> {
                _state.value = _state.value.copy(
                    currentPage = _state.value.currentPage + 1
                )
            }
        }
        }


    fun skipTutorial(){
        viewModelScope.launch {
            insertSkipTutorialUseCase().collect { result ->
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
                        _eventFlow.emit(StartUiEvent.ShowToast("Error: ${result.message}"))

                    }
                    is Resource.Success<*> -> {
                        println("Success brorrrrrr")
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