package com.nayibit.phrasalito_presentation.screens.deckScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Resource
import com.nayibit.common.util.UiText
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.useCases.decks.DeleteDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.GetAllDecksUseCase
import com.nayibit.phrasalito_domain.useCases.decks.InsertDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.UpdateDeckUseCase
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject
     constructor(
    private val insertDeckUseCase: InsertDeckUseCase,
    private val getDecksUseCase : GetAllDecksUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase,
    private val updateDeckUseCase: UpdateDeckUseCase
         )
    : ViewModel()  {

    private val _state = MutableStateFlow(DeckStateUi()) // Initial default state
    val state: StateFlow<DeckStateUi> = _state.asStateFlow()


    private val _eventFlow = MutableSharedFlow<DeckUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


   init {
        getAllDecks()
    }


    fun onEvent(event: DeckUiEvent) {
        when (event) {
            is ShowModal -> {
                _state.value = _state.value.copy(
                    showModal = true,
                    bodyModal = event.type,
                    currentDeck = event.deck,
                    nameDeck = event.deck.name
                )
            }
            is DismissModal -> {
                _state.value = _state.value.copy(
                    showModal = false,
                    isLoadingButton = false,
                    nameDeck = ""
                )
            }
            is  ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }
            is UpdateTextFieldInsert -> {
                _state.value = _state.value.copy(
                    nameDeck = event.text
                )
            }
            is InsertDeck -> {
                val deck = Deck(
                    name = _state.value.nameDeck,
                    maxCards = 0
                )
                insertDeck(deck)
            }
            is NavigationToPhrases -> {
                viewModelScope.launch {
                _eventFlow.emit(NavigationToPhrases(event.id))
              }
            }


            is OpenPrompt -> {
                val phrases = listOf("Hello", "How are you?", "Goodbye")
                val prompt = buildPrompt(phrases)
                val encoded = URLEncoder.encode(prompt, "UTF-8")
                val url = "https://chat.openai.com/?q=$encoded"

                viewModelScope.launch {
                    _eventFlow.emit(OpenPrompt(url, prompt))
                }
            }

            is DeleteDeck -> deleteDeck(event.id)

            is UpdateDeck -> updateDeck(event.id, event.nameDeck)
            is UpdateTextFieldUpdate -> {
                _state.value = _state.value.copy(
                    nameDeck = event.text
                )
            }
        }
    }


    private fun buildPrompt(phrases: List<String>): String {
        return buildString {
            append("Translate these phrases into casual English:\n")
            phrases.forEachIndexed { i, phrase ->
                append("${i+1}. $phrase\n")
            }
        }
    }

    fun updateDeck(id: Int, name: String){
        viewModelScope.launch {
            val result = updateDeckUseCase(id, name)
            when(result){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        showModal = false
                    )
                    _eventFlow.emit(ShowToast(UiText.DynamicString("Error: ${result.message}")))
                }
                is Resource.Success<*> -> {
                    _state.value = _state.value.copy(
                       showModal = false
                    )
                    _eventFlow.emit(ShowToast(UiText.DynamicString("Deck actualizado")))
                }
                else -> {}
            }
        }
    }

    fun deleteDeck(id : Int){
        viewModelScope.launch {
         val result = deleteDeckUseCase(id)
            when(result){
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        showModal = false
                    )
                    _eventFlow.emit(ShowToast(UiText.DynamicString("Error: ${result.message}")))
                }
                is Resource.Success<*> -> {
                    _state.value = _state.value.copy(
                        showModal = false
                    )
                    _eventFlow.emit(ShowToast(UiText.DynamicString("Deck eliminado")))
                }
                else -> {}
            }
        }
    }


    fun insertDeck(deck: Deck) {
            viewModelScope.launch {

               insertDeckUseCase(deck).collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                successInsertedDeck = null,
                                errorMessage = null,
                                isLoadingButton = true
                                )
                        }
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                successInsertedDeck = result.data,
                                errorMessage = null,
                                showModal = false,
                                isLoadingButton = false,
                                nameDeck = ""
                            )
                           _eventFlow.emit(ShowToast(UiText.DynamicString("Deck inserted successfully")))
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = result.message,
                                showModal = false,
                                isLoadingButton = false,
                                nameDeck = ""
                            )
                            _eventFlow.emit(ShowToast(UiText.DynamicString("Error: ${result.message}")))
                        }

                    }

                }
            }
        }

    fun getAllDecks() {
        viewModelScope.launch {
            getDecksUseCase().collect { result ->
                when (result) {
                    is Resource.Loading -> {
                        _state.value = _state.value.copy(
                            isLoading = true)
                    }
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            decks = result.data
                        )
                    }
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                        _eventFlow.emit(ShowToast(UiText.DynamicString("Error: ${result.message}")))
                    }
                }
            }
        }

    }



}

