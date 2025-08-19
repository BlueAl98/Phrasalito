package com.nayibit.phrasalito_presentation.screens.phraseScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.useCases.phrases.DeletebyIdPhraseUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.InsertPhraseUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.UpdatePhraseByIdUseCase
import com.nayibit.phrasalito_domain.utils.Resource
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
        private val getAllPhrasesUseCase: GetAllPhrasesUseCase,
        private val insertPhraseUseCase: InsertPhraseUseCase,
        private val deletePhraseUseCase: DeletebyIdPhraseUseCase,
        private val updatePhraseByIdUseCase: UpdatePhraseByIdUseCase,
        savedStateHandle: SavedStateHandle
    ) : ViewModel()  {

    val idDeck = savedStateHandle.get<Int>("idDeck")

    private val _state = MutableStateFlow(PhraseStateUi())
    val state: StateFlow<PhraseStateUi> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow< PhraseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
         getAllPhrases(idDeck ?: -1)
     }

    fun onEvent(event: PhraseUiEvent) {
        when (event) {
            PhraseUiEvent.DismissModal -> {
                _state.value = _state.value.copy(
                    showModal = false,
                    isLoadingButton = false,
                    firstPhrase = "",
                    translation = "",
                    phraseToUpdate = null
                )
            }
            PhraseUiEvent.InsertPhrase -> {
                _state.update { it.copy(bodyModal = BodyModalEnum.BODY_INSERT_PHRASE) }
                val phrase = Phrase(
                    targetLanguage = _state.value.firstPhrase,
                    translation = _state.value.translation,
                    deckId = idDeck ?: -1
                )
                insertPhrase(phrase)

            }
            is PhraseUiEvent.ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }

            is PhraseUiEvent.UpdateTextFirstPhrase -> {
                _state.update { it.copy(firstPhrase = event.text) }
            }
            is PhraseUiEvent.UpdateTextTraslation -> {
                _state.update { it.copy(translation = event.text) }
            }
            is PhraseUiEvent.ExpandItem -> {
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
            is PhraseUiEvent.CollapsedItem -> {
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

            is PhraseUiEvent.DeletePhrase -> {
                deletePhrase(event.id)
            }

            is PhraseUiEvent.UpdatePhrase -> {
                val phrase = Phrase(
                    id = event.phraseUi.id,
                    targetLanguage = _state.value.firstPhrase,
                    translation = _state.value.translation,
                    deckId = idDeck ?: -1
                )
                updatePhrase(phrase)
            }

            is PhraseUiEvent.ShowModal -> {
                _state.update { it.copy(showModal = true,
                    bodyModal = event.type,
                    firstPhrase = event.phraseUi?.targetLanguage ?: "",
                    translation = event.phraseUi?.translation ?: "",
                    phraseToUpdate = event.phraseUi
                ) }
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
                    _eventFlow.emit(PhraseUiEvent.ShowToast(result.message))
                }
                Resource.Loading -> {
                    _state.update { it.copy(isLoadingButton = true) }
                }
                is Resource.Success<*> -> {
                    _state.update {
                        it.copy(isLoadingButton = false, showModal = false,
                            firstPhrase = "", translation = "")

                    }
                    _eventFlow.emit(PhraseUiEvent.ShowToast("Phrase inserted successfully"))
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
                   //    _eventFlow.emit(PhraseUiEvent.DismissModal)
                       _eventFlow.emit(PhraseUiEvent.ShowToast(result.message))
                   }
                   is Resource.Success<*> -> {
                       _state.update { it.copy(isLoadingButton = false,  showModal = false, phraseToUpdate = null) }
                    //  _eventFlow.emit(PhraseUiEvent.DismissModal)
                       _eventFlow.emit(PhraseUiEvent.ShowToast("Phrase updated successfully"))
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
                        is Resource.Success -> _state.update {
                            it.copy(
                                isLoading = false,
                                phrases = result.data.map { phrase ->
                                    PhraseUi(
                                        id = phrase.id,
                                        targetLanguage = phrase.targetLanguage,
                                        translation = phrase.translation,
                                        isOptionsRevealed = false)
                                }
                            )
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _eventFlow.emit(PhraseUiEvent.ShowToast(result.message))
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
                        _eventFlow.emit(PhraseUiEvent.ShowToast(it.message))
                    }
                    Resource.Loading -> {
                      _state.update { it.copy(isLoadingButton = true) }
                    }
                    is Resource.Success<*> -> {
                        _state.update { it.copy(isLoadingButton = false, showModal = false, phraseToUpdate = null) }
                        _eventFlow.emit(PhraseUiEvent.ShowToast("Frase eliminada"))
                    }
              }
         }

      }
    }


}