package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.common.util.normalizeSpaces
import com.nayibit.phrasalito_domain.useCases.phrases.GetPhrasesByDeckReadyForTestUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsSpeakingUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsTextSpeechReadyUseCase
import com.nayibit.phrasalito_domain.useCases.tts.SpeakTextUseCase
import com.nayibit.phrasalito_presentation.mappers.toExerciseUI
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnInputChanged
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnNextPhrase
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnSpeakPhrase
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.OnStartClicked
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.ShowAllInfo
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.ShowDialog
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.ShowSnackBar
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseUiEvent.UpdateExpandedState
import com.nayibit.phrasalito_presentation.utils.calculateProgressPercentage
import com.nayibit.phrasalito_presentation.utils.textWithoutSpecialCharacters
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllPhrasesByDeckUseCase: GetPhrasesByDeckReadyForTestUseCase,
    private val speakTextUseCase: SpeakTextUseCase,
    private val isTTsAvailableUseCase: IsTextSpeechReadyUseCase,
    private val isSpeakingUseCase: IsSpeakingUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val idDeck = savedStateHandle.get<Int>("idDeck") ?: -1
    val lngCode = savedStateHandle.get<String>("lngCode") ?: ""


    private val _state = MutableStateFlow(ExerciseUiState(
        lngCode = lngCode
    ))
    val state: StateFlow<ExerciseUiState> = _state.asStateFlow()

    private val _eventChannel = Channel<ExerciseUiEvent>()
    val eventFlow: Flow<ExerciseUiEvent> = _eventChannel.receiveAsFlow()

    private var isTtsPrewarmed = false

    init {
        getAllPhrases(idDeck)
        ttsSetUp()
    }

    fun onEvent(event: ExerciseUiEvent) {
        when (event) {

            is UpdateExpandedState -> {
                _state.update { it.copy(popOverState = event.expanded) }
            }
            is OnInputChanged -> {
                _state.update { it.copy(inputAnswer = event.input) }

              if (_state.value.phrases[event.currentIndex].targetLanguage?.textWithoutSpecialCharacters() == event.input.normalizeSpaces()) {
                  _state.update { currentState ->
                      currentState.copy(
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
                    _eventChannel.send(ShowSnackBar("Exercise Started!"))
                }
            }
            is OnNextPhrase -> {

                _state.update { it.copy(popOverState = false) }

                if (event.currentIndex == _state.value.phrases.size - 1) {
                    viewModelScope.launch {
                        _state.update { it.copy(
                            isBottomSheetExpanded = true,
                            testCompleted = true,
                            testProgressCorrectAnswers = calculateProgressPercentage(
                                total = it.totalItems ,
                                completed = it.phrases.filter { it.phraseState == PhraseState.COMPLETED }.size
                            )
                        ) }
                        _eventChannel.send(OnNextPhrase(event.currentIndex))
                    }
                    return
                }

                _state.update { currentState ->
                   currentState.copy(
                       isBottomSheetExpanded = false,
                       inputAnswer = "",
                       currentIndex = currentState.currentIndex + 1,
                       testProgressCorrectAnswers = calculateProgressPercentage(
                           total = currentState.totalItems ,
                           completed = currentState.phrases.filter { it.phraseState == PhraseState.COMPLETED }.size
                       )

                   )
               }

            }

            is OnSpeakPhrase -> {
                speakTextUseCase(event.text, _state.value.lngCode)
            }

            is ShowDialog -> {
              _state.update { it.copy(showDialog = event.show, bodyModalExercise = event.type) }
            }
            is ShowAllInfo -> {
                _state.update { currentState ->
                    currentState.copy(
                        showDialog = false,
                        inputAnswer = "",
                        phrases = currentState.phrases.mapIndexed { index, phrase ->
                            if (index == event.currentIndex) phrase.copy(
                                example = phrase.correctAnswer,
                                phraseState = PhraseState.ERROR_ANSWER
                            )
                            else phrase
                        }
                    )
                }
            }
            is ExerciseUiEvent.NavigateNext -> {
                viewModelScope.launch {
                    _eventChannel.send(ExerciseUiEvent.NavigateNext)
                }
            }
            is ShowSnackBar -> {
                viewModelScope.launch {
                    _eventChannel.send(ShowSnackBar(event.message))
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
                                           totalItems = result.data.size,
                                           testProgressCorrectAnswers = 0f
                                       )
                                   }
                               }
                            }
                            is Resource.Error -> {
                                _state.update { it.copy(isLoading = false) }
                            }
                        }
                    }
            }
        }

    private fun prewarmTts() {
        if (isTtsPrewarmed) return
        isTtsPrewarmed = true
        viewModelScope.launch {
            speakTextUseCase(" ", lngCode)
        }
    }

    private suspend fun observeTtsSpeaking() {
        isSpeakingUseCase()
            .drop(2)
            .collect { isSpeaking ->
                if (isTtsPrewarmed)
                    _state.update { it.copy(isTtsSpeaking = isSpeaking) }
            }

    }

    private suspend fun getStateTTS(){
        isTTsAvailableUseCase().collect { result ->
            when (result) {
                is Resource.Error -> {

                    _state.update { it.copy(isTTsReady = false) }
                }

                is Resource.Success<*> -> {
                    _state.update {
                        it.copy(isTTsReady = true)
                    }
                    prewarmTts()
                }

                Resource.Loading -> {}
            }
        }
    }


    private fun ttsSetUp(){
        viewModelScope.launch {
            if (lngCode != ""){
                getStateTTS()
                observeTtsSpeaking()
            }
        }
    }


}
