package com.nayibit.phrasalito_presentation.screens.deckScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.common.util.Constants.FIRST_ELEMENT_DROP_LANGUAGE_LIST
import com.nayibit.common.util.Constants.MIN_CHAR_NAME_DECK
import com.nayibit.common.util.Resource
import com.nayibit.common.util.UiText.DynamicString
import com.nayibit.common.util.UiText.StringResource
import com.nayibit.common.util.countValidChar
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.useCases.dataStore.InsertTutorialDeckUseCase
import com.nayibit.phrasalito_domain.useCases.dataStore.IsTutorialDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.DeleteDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.GetAllDecksUseCase
import com.nayibit.phrasalito_domain.useCases.decks.InsertDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.UpdateDeckUseCase
import com.nayibit.phrasalito_domain.useCases.tts.GetAvailableLanguagesUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsTextSpeechReadyUseCase
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.mappers.toDeck
import com.nayibit.phrasalito_presentation.mappers.toDeckUI
import com.nayibit.phrasalito_presentation.mappers.toLanguage
import com.nayibit.phrasalito_presentation.model.Language
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.DeleteDeck
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.DismissModal
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.InsertDeck
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.NavigationToPhrases
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.OpenPrompt
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.ResetAllSwiped
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.ShowModal
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.ShowSnackbar
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.ShowToast
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.UpdateDeck
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.UpdateDeckList
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.UpdateTextFieldInsert
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.UpdateTextFieldUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class DeckViewModel @Inject
     constructor(
    private val insertDeckUseCase: InsertDeckUseCase,
    private val getDecksUseCase : GetAllDecksUseCase,
    private val deleteDeckUseCase: DeleteDeckUseCase,
    private val updateDeckUseCase: UpdateDeckUseCase,
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val isTextSpeechReadyUseCase: IsTextSpeechReadyUseCase,
    private val isTutorialDeckUseCase: IsTutorialDeckUseCase,
    private val insertTutorialDeckUseCase: InsertTutorialDeckUseCase)
    : ViewModel() {

    private val _state = MutableStateFlow(DeckStateUi()) // Initial default state
    val state: StateFlow<DeckStateUi> = _state.asStateFlow()


    private val _eventFlow = MutableSharedFlow<DeckUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    init {
        getAllDecks()
        getTutorialState()
        getAvailableLanguages()
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
                        insertDeck(_state.value.currentDeck.toDeck())
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

            is DeleteDeck -> deleteDeck(event.id)

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
                        updateDeck(_state.value.currentDeck)
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
                viewModelScope.launch {
                    insertTutorialDeckUseCase()
                }
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

    fun updateDeck(
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

                else -> {}
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
            isTutorialDeckUseCase().collect{ result ->
                when (result){
                    is Resource.Success -> {
                        _state.value = _state.value.copy(
                            showTutorial = result.data
                        )
                    }
                    else -> {}
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
                            isLoading = true
                        )
                    }

                    is Resource.Success -> {

                        _state.value = _state.value.copy(
                            decks = result.data.map { it.toDeckUI() })
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

    }

    @OptIn(ExperimentalCoroutinesApi::class)
     fun getAvailableLanguages() {
    viewModelScope.launch {
            isTextSpeechReadyUseCase()
                .flatMapLatest { result ->
                    when (result) {
                        is Resource.Success -> {
                            // Only proceed if TTS is ready
                            getAvailableLanguagesUseCase()
                        }
                        is Resource.Error -> {
                            _state.value = _state.value.copy(isLoading = false)
                            flowOf(Resource.Error(result.message))
                        }
                        else -> {
                            flowOf(Resource.Loading)
                        }
                    }
                }
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                listLanguages = if (result.data.isNotEmpty()){
                                    listOf(Language(id = -1, language = FIRST_ELEMENT_DROP_LANGUAGE_LIST, alias = "")) + result.data.map { it.toLanguage() }
                                } else emptyList()
                            )
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                errorMessage = result.message
                            )
                            _eventFlow.emit(ShowToast(DynamicString("Error: ${result.message}")))
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(isLoading = true)
                        }
                    }
                }
        }
}
    }
