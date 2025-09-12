package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesByDeckUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsTextSpeechReadyUseCase
import com.nayibit.phrasalito_domain.useCases.tts.ShutDownTtsUseCase
import com.nayibit.phrasalito_domain.useCases.tts.SpeakTextUseCase
import com.nayibit.phrasalito_presentation.mappers.toExerciseUI
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnInputChanged
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnNextPhrase
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnSpeakPhrase
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnStartClicked
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.ShowAllInfo
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.ShowDialog
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.UpdateExpandedState
import com.nayibit.phrasalito_presentation.utils.textWithoutSpecialCharacters
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
    private val speakTextUseCase: SpeakTextUseCase,
    private val shutDownTtsUseCase: ShutDownTtsUseCase,
    private val isTextSpeechReadyUseCase: IsTextSpeechReadyUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val idDeck = savedStateHandle.get<Int>("idDeck") ?: -1

    private val _state = MutableStateFlow(ExerciseUiState())
    val state: StateFlow<ExerciseUiState> = _state.asStateFlow()

    private val _eventChannel = Channel<ExerciseUiEvent>()
    val eventFlow: Flow<ExerciseUiEvent> = _eventChannel.receiveAsFlow()


    init {
        observeTextToSpeechReady()
        getAllPhrases(idDeck)
    }

    fun onEvent(event: ExerciseUiEvent) {
        when (event) {

            is UpdateExpandedState -> {
                _state.update { it.copy(popOverState = event.expanded) }
            }
            is OnInputChanged -> {
                _state.update { it.copy(inputAnswer = event.input) }

              if (_state.value.phrases[event.currentIndex].targetLanguage.textWithoutSpecialCharacters() == event.input) {
                  _state.update { currentState ->
                      currentState.copy(
                         // currentIndex = event.currentIndex + 1,
                          phrases = currentState.phrases.mapIndexed { index, phrase ->
                              if (index == event.currentIndex) phrase.copy(example = phrase.correctAnswer, phraseState = PhraseState.COMPLETED)
                              else phrase
                          }
                      )
                  }
              }
            }

            is OnStartClicked -> {
                viewModelScope.launch {
                    _eventChannel.send(ExerciseUiEvent.ShowToast("Exercise Started!"))
                }
            }
            is OnNextPhrase -> {

                _state.update { it.copy(popOverState = false) }

                if (event.currentIndex == _state.value.phrases.size - 1) {
                    viewModelScope.launch {
                        _state.update { it.copy(testCompleted = true) }
                        _eventChannel.send(OnNextPhrase(event.currentIndex))
                    }
                    return
                }

                _state.update { currentState ->
                   currentState.copy(
                       inputAnswer = "",
                       currentIndex = currentState.currentIndex + 1
                   )
               }
            }

            is OnSpeakPhrase -> {
                if (_state.value.ttsState)
                    speakTextUseCase(event.text)
                else
                    viewModelScope.launch {
                        _eventChannel.send(ExerciseUiEvent.ShowToast("TTS is not ready"))
                    }
            }

            is ShowDialog -> {
              _state.update { it.copy(showDialog = event.show) }
            }
            is ShowAllInfo -> {
                _state.update { currentState ->
                    currentState.copy(
                        showDialog = false,
                        inputAnswer = "",
                        phrases = currentState.phrases.mapIndexed { index, phrase ->
                            if (index == event.currentIndex) phrase.copy(example = phrase.correctAnswer, phraseState = PhraseState.ERROR_ANSWER)
                            else phrase
                        }
                    )
                }
            }

            else -> Unit
        }


    }

    private fun observeTextToSpeechReady() {
        viewModelScope.launch {
            isTextSpeechReadyUseCase().collect { result ->
                when (result){
                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                    is Resource.Success -> _state.update { it.copy(isLoading = false, ttsState = true) }
                    is Resource.Error -> _state.update { it.copy(isLoading = false, ttsState = false) }
                }
            }
        }
    }


    fun getAllPhrases(idDeck: Int) {
        viewModelScope.launch {

                getAllPhrasesByDeckUseCase(idDeck)
                    .collect { result ->
                        when (result) {
                            is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                            is Resource.Success -> {
                               if (_state.value.phrases.isEmpty()) {
                                   _state.update {
                                       it.copy(
                                           isLoading = false,
                                           phrases = result.data.shuffled().map { it.toExerciseUI() },
                                           totalItems = result.data.size
                                       )
                                   }
                               }
                            }
                            is Resource.Error -> {
                                _state.update { it.copy(isLoading = false) }
                                //  _eventFlow.emit(PhraseUiEvent.ShowToast(UiText.DynamicString(result.message)))
                            }
                        }
                    }
            }
        }
    }
