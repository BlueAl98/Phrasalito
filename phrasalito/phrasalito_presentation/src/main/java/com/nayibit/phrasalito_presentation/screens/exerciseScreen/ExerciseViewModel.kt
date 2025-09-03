package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesByDeckUseCase
import com.nayibit.phrasalito_presentation.mappers.toExerciseUI
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnCheckClicked
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnInputChanged
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnStartClicked
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.UpdateExpandedState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllPhrasesByDeckUseCase: GetAllPhrasesByDeckUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val idDeck = savedStateHandle.get<Int>("idDeck")

    private val _state = MutableStateFlow(ExerciseUiState())
    val state: StateFlow<ExerciseUiState> = _state.asStateFlow()

    private val _eventChannel = Channel<ExerciseUiEvent>()
    val eventFlow: Flow<ExerciseUiEvent> = _eventChannel.receiveAsFlow()


    init {
        getAllPhrases(idDeck ?: -1)
    }

    fun onEvent(event: ExerciseUiEvent) {
        when (event) {

            is UpdateExpandedState -> {
                _state.update { it.copy(popOverState = event.expanded) }
            }
            is OnInputChanged -> {
                _state.update { it.copy(inputAnswer = event.input) }
            }
            is OnStartClicked -> {
                viewModelScope.launch {
                    _eventChannel.send(ExerciseUiEvent.ShowToast("Exercise Started!"))
                }
            }
            is OnCheckClicked -> {
                viewModelScope.launch {

                    _state.update { currentState ->
                        currentState.copy(
                           // currentIndex = event.currentIndex + 1,
                            phrases = currentState.phrases.mapIndexed { index, phrase ->
                                if (index == event.currentIndex) phrase.copy(example = phrase.correctAnswer)
                                else phrase
                            }
                        )
                    }

                 /*   _state.value = _state.value.copy(
                        currentIndex = event.currentIndex,
                        inputAnswer = ""
                    )*/
                }
            }
            else -> Unit
        }
    }


    fun getAllPhrases(idDeck: Int) {
        viewModelScope.launch {
            getAllPhrasesByDeckUseCase(idDeck)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                        is Resource.Success -> _state.update {
                          it.copy(
                              isLoading = false,
                              phrases = result.data.map { phrase ->
                                  phrase.toExerciseUI()
                              },
                              totalItems = result.data.size
                          )
                        }
                        is Resource.Error -> {
                         //   _state.update { it.copy(isLoading = false) }
                          //  _eventFlow.emit(PhraseUiEvent.ShowToast(UiText.DynamicString(result.message)))
                        }
                    }
                }
        }
    }

}