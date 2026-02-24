package com.nayibit.feature_deckscreen.presentation.deckScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.feature_deckscreen.R
import com.nayibit.feature_deckscreen.domain.useCases.decks.DeleteDeckUseCase
import com.nayibit.feature_deckscreen.domain.useCases.decks.GetAllDecksUseCase
import com.nayibit.feature_deckscreen.domain.useCases.decks.InsertDeckUseCase
import com.nayibit.feature_deckscreen.domain.useCases.decks.UpdateDeckUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject
import com.nayibit.feature_deckscreen.presentation.deckScreen.DeckUiEvent.*
import com.nayibit.feature_deckscreen.presentation.mappers.toDeckUI
import com.nayibit.utils.Constants.MIN_CHAR_NAME_DECK
import com.nayibit.utils.helpers.UiText.*
import com.nayibit.utils.helpers.UiText.StringResource
import com.nayibit.utils.helpers.countValidChar
import com.nayibit.utils.helpers.onError
import com.nayibit.utils.helpers.onSuccess

@HiltViewModel
class DeckViewModel @Inject
     constructor(
    private val insertDeckUseCase: InsertDeckUseCase,
    private val getDecksUseCase : GetAllDecksUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase,
    private val updateDeckUseCase: UpdateDeckUseCase)
    : ViewModel() {

    private val _state = MutableStateFlow(DeckStateUi()) // Initial default state
    val state: StateFlow<DeckStateUi> = _state.asStateFlow()


    private val _eventFlow = MutableSharedFlow<DeckUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        getAllDecks()
        //getAllDecks()
        //getTutorialState()
    }


    fun onEvent(event: DeckUiEvent) {
        when (event) {
            is ShowModal -> {
                _state.value = _state.value.copy(
                    showModal = true,
                    bodyModal = event.type,
                    currentDeck = event.deck)
            }

            is DismissModal -> {
                _state.value = _state.value.copy(
                    showModal = false,
                    isLoadingButton = false,
                    decks = _state.value.decks.map { it.copy(isSwiped = false) },
                    currentDeck = DeckUI()
                )
            }

            is ShowToast -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }

            is UpdateTextFieldInsert -> {
                _state.value = _state.value.copy(
                    currentDeck = _state.value.currentDeck.copy(name = event.text)
                )
            }

            is InsertDeck -> {
                when{
                    _state.value.currentDeck.name.isEmpty() -> {
                        viewModelScope.launch {
                            _eventFlow.emit(ShowToast(StringResource(R.string.label_empty_deck_name)))
                        }
                    }

                    _state.value.currentDeck.name.countValidChar() < MIN_CHAR_NAME_DECK ->{
                        viewModelScope.launch {
                            _eventFlow.emit(ShowToast(StringResource(R.string.label_short_deck_name)))
                        }
                    }
                    

                    else -> {
                     //   insertDeck(_state.value.currentDeck.toDeck())
                    }
                }

            }

            is NavigationToPhrases -> {
                viewModelScope.launch {
                    _state.value = _state.value.copy(
                        decks = _state.value.decks.map { it.copy(isSwiped = false) }
                    )
                    _eventFlow.emit(NavigationToPhrases(event.id, event.lngCode))
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

            is DeleteDeck ->{} //deleteDeck(event.id)

            is UpdateDeck ->{

                when{
                    _state.value.currentDeck.name.isEmpty() -> {
                        viewModelScope.launch {
                            _eventFlow.emit(ShowToast(StringResource(R.string.label_empty_deck_name)))
                        }
                    }

                    _state.value.currentDeck.name.countValidChar() < MIN_CHAR_NAME_DECK ->{
                        viewModelScope.launch {
                            _eventFlow.emit(ShowToast(StringResource(R.string.label_short_deck_name)))
                        }
                    }

                    else -> {
                      //  updateDeck(_state.value.currentDeck)
                    }
                }
            }
            is UpdateTextFieldUpdate -> {
                _state.value = _state.value.copy(
                    currentDeck  = _state.value.currentDeck.copy(name = event.text)
                )
            }

            is UpdateDeckList -> {
                val listDecks = _state.value.decks.map {
                    if (it.id == event.idDeck) it.copy(isSwiped = event.isSwiped)
                    else it.copy(isSwiped = false)
                }
                _state.value = state.value.copy(decks = listDecks)
            }

            is ResetAllSwiped -> {
                _state.value = _state.value.copy(
                    decks = _state.value.decks.map { it.copy(isSwiped = false) }
                )
            }

            is ShowSnackbar -> {
                viewModelScope.launch {
                    _eventFlow.emit(event)
                }
            }

            is DeckUiEvent.OnLanguageSelected -> {
                _state.value = _state.value.copy(
                    currentDeck = _state.value.currentDeck.copy(selectedLanguage = event.language)
                )
            }

            is DeckUiEvent.UpdateNotificationState -> {
                _state.value = _state.value.copy(
                    currentDeck = _state.value.currentDeck.copy(isNotified = event.isNotified)
                )
            }

            DeckUiEvent.TutorialFinish -> {
               // finishTutorial()
            }

            DeckUiEvent.onNextStep -> {
                _state.value = _state.value.copy(
                    currentStep = _state.value.currentStep + 1
                )
            }
        }
    }


    private fun buildPrompt(phrases: List<String>): String {
        return buildString {
            append("Translate these phrases into casual English:\n")
            phrases.forEachIndexed { i, phrase ->
                append("${i + 1}. $phrase\n")
            }
        }
    }

    fun getAllDecks(){
        viewModelScope.launch {
            getDecksUseCase().collect { result ->
                result.onSuccess { decks ->
                    _state.value = _state.value.copy(
                        decks = decks.map { it.toDeckUI() },
                        isLoading = false
                    )

                }.onError { error ->
                    println(error)
                }
            }
        }
    }


  /*  fun updateDeck(
       deckUI: DeckUI
    ) {
        viewModelScope.launch {
            when (val result = updateDeckUseCase(deckUI.toDeck())) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        showModal = false,
                        errorMessage = result.message
                    )
                    _eventFlow.emit(ShowToast(DynamicString("Error: ${result.message}")))
                }

                is Resource.Success<*> -> {
                    _state.value = _state.value.copy(
                        showModal = false
                    )
                    _eventFlow.emit(ShowToast(DynamicString("Deck actualizado")))
                }

            }
        }
    }

    fun deleteDeck(id: Int) {
        viewModelScope.launch {
            val result = deleteDeckUseCase(id)
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        showModal = false
                    )
                    _eventFlow.emit(ShowToast(DynamicString("Error: ${result.message}")))
                }

                is Resource.Success<*> -> {
                    _state.value = _state.value.copy(
                        showModal = false
                    )
                    _eventFlow.emit(ShowToast(DynamicString("Deck eliminado")))
                }
            }
        }
    }


    fun insertDeck(deck: Deck) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = false,
                successInsertedDeck = null,
                errorMessage = null,
                isLoadingButton = true
            )
            insertDeckUseCase(deck).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            successInsertedDeck = result.data.toDeckUI(),
                            errorMessage = null,
                            showModal = false,
                            isLoadingButton = false)
                        _eventFlow.emit(ShowToast(DynamicString("Deck inserted successfully")))
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message,
                            showModal = false,
                            isLoadingButton = false
                        )
                        _eventFlow.emit(ShowToast(DynamicString("Error: ${result.message}")))
                    }

                }

            }
        }
    }

    fun getTutorialState(){
        viewModelScope.launch {
                when (val result = isTutorialDeckUseCase() ){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            showTutorial = result.data
                        )
                    }
                    else -> {}
                }
            }
    }

    fun finishTutorial(){
        viewModelScope.launch {
            _state.value = _state.value.copy(
                showTutorial = false
            )
            insertTutorialDeckUseCase()
        }
    }

    fun getAllDecks() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            getDecksUseCase().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            decks = result.data.map { it.toDeckUI() },
                            isLoading = false
                        )
                    }

                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            errorMessage = result.message
                        )
                        _eventFlow.emit(ShowToast(DynamicString("Error: ${result.message}")))
                    }
                }
            }
        }

    }*/


    }


