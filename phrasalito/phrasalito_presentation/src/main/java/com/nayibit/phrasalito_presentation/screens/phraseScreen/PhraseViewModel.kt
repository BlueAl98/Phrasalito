package com.nayibit.phrasalito_presentation.screens.phraseScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.common.util.UiText.DynamicString
import com.nayibit.common.util.UiText.StringResource
import com.nayibit.common.util.normalizeSpaces
import com.nayibit.common.util.removeLonelySigns
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.useCases.phrases.DeletebyIdPhraseUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesByDeckUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.InsertPhraseUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.UpdatePhraseByIdUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsSpeakingUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsTextSpeechReadyUseCase
import com.nayibit.phrasalito_domain.useCases.tts.SpeakTextUseCase
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.mappers.toPhrase
import com.nayibit.phrasalito_presentation.mappers.toPhraseUi
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.CollapsedItem
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.DeletePhrase
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.DismissModal
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.ExpandItem
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.InsertPhrase
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.Navigation
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.ShowModal
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.ShowSnackbar
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.ShowToast
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.UpdatePhrase
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.UpdateTextExample
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.UpdateTextFirstPhrase
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.UpdateTextTraslation
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUiEvent.UploadCurrentIndexCard
import com.nayibit.phrasalito_presentation.utils.ValidateExampleResult
import com.nayibit.phrasalito_presentation.utils.validateExample
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhraseViewModel
    @Inject constructor(
        private val getAllPhrasesUseCase: GetAllPhrasesByDeckUseCase,
        private val insertPhraseUseCase: InsertPhraseUseCase,
        private val deletePhraseUseCase: DeletebyIdPhraseUseCase,
        private val updatePhraseByIdUseCase: UpdatePhraseByIdUseCase,
        private val speakTextUseCase: SpeakTextUseCase,
        private val isTTsAvailableUseCase: IsTextSpeechReadyUseCase,
        private val isSpeakingUseCase: IsSpeakingUseCase,
        savedStateHandle: SavedStateHandle
    ) : ViewModel()  {

    val idDeck = savedStateHandle.get<Int>("idDeck") ?: -1
    val lngCode = savedStateHandle.get<String>("lngCode") ?: ""

    private val _state = MutableStateFlow(PhraseStateUi(
        idDeck = idDeck,
        lngCode = lngCode
    ))
    val state: StateFlow<PhraseStateUi> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow< PhraseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
         getAllPhrases(idDeck)
         getStateTTS()
         observeTtsSpeaking()
     }

    fun onEvent(event: PhraseUiEvent) {
        when (event) {
            DismissModal -> {
                _state.value = _state.value.copy(
                    showModal = false,
                    isLoadingButton = false,
                    firstPhrase = "",
                    translation = "",
                    phraseToUpdate = null,
                    example = ""
                )
            }

            InsertPhrase -> { viewModelScope.launch {
                _state.update { it.copy(bodyModal = BodyModalEnum.BODY_INSERT_PHRASE,
                    firstPhrase = _state.value.firstPhrase.removeLonelySigns()) }

                val result = validateExample(_state.value.firstPhrase, _state.value.example)

                when (result) {
                    ValidateExampleResult.IS_VALID -> {
                        insertPhrase(Phrase(
                            targetLanguage = _state.value.firstPhrase.normalizeSpaces(),
                            translation = _state.value.translation.normalizeSpaces(),
                            deckId = idDeck,
                            example = _state.value.example.normalizeSpaces()
                        ))
                    }

                    ValidateExampleResult.EXAMPLE_NOT_CONTAINS_PHRASE -> {
                        _eventFlow.emit(
                            ShowToast(StringResource(R.string.error_example_not_contains_phrase))
                        )

                    }

                    ValidateExampleResult.EXAMPLE_IS_NOT_LONGER_THAN_PHRASE -> {
                        _eventFlow.emit(
                            ShowToast(StringResource(R.string.error_example_is_not_longer_than_phrase))
                        )
                    }
                }
            }


            }
            is ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }

            is UpdateTextFirstPhrase -> {

                _state.update { it.copy(firstPhrase = event.text) }
            }
            is UpdateTextTraslation -> {
                _state.update { it.copy(translation = event.text) }
            }

            is UpdateTextExample -> {
                _state.update { it.copy(example = event.text) }
            }

            is ExpandItem -> {
                _state.update { state ->
                    state.copy(
                        phrases = state.phrases.map { phrase ->
                            if (phrase.id == event.id) {
                                phrase.copy(isOptionsRevealed = true)
                            } else phrase
                        }
                    )
                }
             }
            is CollapsedItem -> {
                _state.update { state ->
                    state.copy(
                        phrases = state.phrases.map { phrase ->
                            if (phrase.id == event.id) {
                                phrase.copy(isOptionsRevealed = false)
                                } else phrase
                        }
                    )
                }
            }

            is DeletePhrase -> {
                deletePhrase(event.id)
            }

            is UpdatePhrase -> { viewModelScope.launch {

                val result = validateExample(_state.value.firstPhrase, _state.value.example)

                when (result) {
                    ValidateExampleResult.IS_VALID -> {
                        updatePhrase(event.phraseUi.toPhrase()
                            .copy(
                                targetLanguage = _state.value.firstPhrase.normalizeSpaces(),
                                translation = _state.value.translation.normalizeSpaces(),
                                example = _state.value.example.normalizeSpaces(),
                                deckId = idDeck
                            ))
                    }
                    ValidateExampleResult.EXAMPLE_NOT_CONTAINS_PHRASE -> {
                            _eventFlow.emit(
                                ShowToast(StringResource(R.string.error_example_not_contains_phrase))
                            )

                    }
                    ValidateExampleResult.EXAMPLE_IS_NOT_LONGER_THAN_PHRASE -> {
                            _eventFlow.emit(
                                ShowToast(StringResource(R.string.error_example_is_not_longer_than_phrase))
                            )
                        }
                    }
                }

            }

            is ShowModal -> {

                _state.update { it.copy(showModal = true,
                    bodyModal = event.type,
                    firstPhrase = event.phraseUi?.targetLanguage ?: "",
                    translation = event.phraseUi?.translation ?: "",
                    phraseToUpdate = event.phraseUi,
                    example = event.phraseUi?.example ?: ""
                ) }
            }
            is UploadCurrentIndexCard -> {
                if (!event.reset)
                    _state.update { it.copy(curentCardPhrase = it.curentCardPhrase + 1) }
                else
                   _state.update { it.copy(curentCardPhrase = 0) }
            }

            is Navigation -> {
                viewModelScope.launch {
                    _state.update { it.copy(showModal = false) }
                    _eventFlow.emit(event)
                }
            }

            is ShowSnackbar -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }

            is PhraseUiEvent.SpeakText -> {
                speakTextUseCase(event.text, lngCode)
            }
        }
    }


     fun getStateTTS(){
         viewModelScope.launch {
             isTTsAvailableUseCase().collect { result ->
                 when (result) {
                     is Resource.Error -> {
                         _state.update { it.copy(isTTsReady = false) }
                     }

                     is Resource.Success<*> -> {
                         _state.update {
                             it.copy(isTTsReady = true)
                         }
                     }

                     Resource.Loading -> {}
                 }
             }
         }
    }

    fun insertPhrase(phrase: Phrase){
        viewModelScope.launch {
        insertPhraseUseCase(phrase).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update {
                        it.copy(isLoadingButton = false, showModal = false,
                            firstPhrase = "", translation = "")
                    }
                    _eventFlow.emit(ShowSnackbar(DynamicString(result.message)))
                }
                Resource.Loading -> {
                    _state.update { it.copy(isLoadingButton = true) }
                }
                is Resource.Success<*> -> {
                    _state.update {
                        it.copy(isLoadingButton = false, showModal = false,
                            firstPhrase = "", translation = "")

                    }
                    _eventFlow.emit(ShowSnackbar(StringResource(R.string.label_phrase_inserted_success)))
                }
            }

        }
        }
    }

    fun updatePhrase(phrase: Phrase){
        viewModelScope.launch {
           updatePhraseByIdUseCase(phrase).collect { result ->
               when (result) {
                   is Resource.Loading -> {
                       _state.update { it.copy(isLoadingButton = true) }
                   }
                   is Resource.Error -> {
                       _state.update { it.copy(isLoadingButton = false, showModal = false, phraseToUpdate = null) }
                       _eventFlow.emit(ShowSnackbar(DynamicString(result.message)))
                   }
                   is Resource.Success<*> -> {
                       _state.update { it.copy(isLoadingButton = false,  showModal = false, phraseToUpdate = null) }
                       _eventFlow.emit(ShowSnackbar(StringResource(R.string.label_phrase_updated_success)))
                   }
               }
           }
        }

    }


     fun getAllPhrases(idDeck: Int) {
        viewModelScope.launch {
            getAllPhrasesUseCase(idDeck)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                        is Resource.Success -> {
                            _state.update {
                            it.copy(
                                isLoading = false,
                                phrases = result.data.map { phrase ->
                                    phrase.toPhraseUi()
                                }
                            )
                        }
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _eventFlow.emit(ShowSnackbar(DynamicString(result.message)))
                        }
                    }
                }
        }
    }

    fun deletePhrase(id: Int) {
        viewModelScope.launch {
            deletePhraseUseCase(id).collect {
                when (it) {
                    is Resource.Error -> {
                        _state.update { it.copy(isLoadingButton = false, showModal = false, phraseToUpdate = null) }
                        _eventFlow.emit(ShowSnackbar(DynamicString(it.message)))
                    }
                    Resource.Loading -> {
                      _state.update { it.copy(isLoadingButton = true) }
                    }
                    is Resource.Success<*> -> {
                        _state.update { it.copy(isLoadingButton = false, showModal = false, phraseToUpdate = null) }
                        _eventFlow.emit(ShowSnackbar(StringResource(R.string.phrase_deleted_successfully)))
                    }
              }
         }

      }
    }

    private fun observeTtsSpeaking() {
        viewModelScope.launch {
            isSpeakingUseCase().collect { isSpeaking ->
                _state.update { it.copy(isTtsSpeaking = isSpeaking) }
            }
        }
    }

}