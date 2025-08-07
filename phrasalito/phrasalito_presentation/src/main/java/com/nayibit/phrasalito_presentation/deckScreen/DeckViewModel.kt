package com.nayibit.phrasalito_presentation.deckScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.useCases.decks.InsertDeckUseCase
import com.nayibit.phrasalito_domain.utils.Resource
import com.nayibit.phrasalito_presentation.deckScreen.DeckUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject
     constructor(private val insertDeckUseCase: InsertDeckUseCase): ViewModel()  {

    private val _state = MutableStateFlow(DeckStateUi()) // Initial default state
    val state: StateFlow<DeckStateUi> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<DeckUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun insertDeck(deck: Deck) {
            viewModelScope.launch {

               insertDeckUseCase(deck).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true,
                                successInsertedDeck = null,
                                errorMessage = null)
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                successInsertedDeck = result.data,
                                errorMessage = null
                            )
                           _eventFlow.emit(ShowToast("Deck inserted successfully ${result.data}"))
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                        //    _eventFlow.emit(DeckUiEvent.ShowToast("Error: ${result.message}"))
                        }

                    }

                }
            }
        }
    }

