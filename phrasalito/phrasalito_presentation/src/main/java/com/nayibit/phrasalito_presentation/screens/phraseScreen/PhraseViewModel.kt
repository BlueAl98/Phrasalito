package com.nayibit.phrasalito_presentation.screens.phraseScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.InsertPhraseUseCase
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
                    isLoadingButton = false
                )
            }
            PhraseUiEvent.InsertDeck -> {
                val phrase = Phrase(
                    targetLanguage = "hello",
                    translation = "hola",
                    deckId = idDeck ?: -1
                )
                insertPhrase(phrase)

            }
            PhraseUiEvent.ShowModal -> {
                _state.update { it.copy(showModal = true) }
            }
            is PhraseUiEvent.ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }
        }
    }

    fun insertPhrase(phrase: Phrase){
        viewModelScope.launch {
        insertPhraseUseCase(phrase).collect { result ->
            when (result) {
                is Resource.Error -> {
                    _state.update { it.copy(isLoadingButton = false) }
                    _eventFlow.emit(PhraseUiEvent.ShowToast(result.message))
                }
                Resource.Loading -> {
                    _state.update { it.copy(isLoadingButton = true) }
                }
                is Resource.Success<*> -> {
                    _state.update { it.copy(isLoadingButton = false) }
                    _eventFlow.emit(PhraseUiEvent.ShowToast("Phrase inserted successfully"))
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
                                phrases = result.data
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


}