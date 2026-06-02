package com.nayibit.feature_phrases.presentation.phraseScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.datastore.data.GenericDataStore
import com.nayibit.feature_phrases.R
import com.nayibit.feature_phrases.domain.model.Phrase
import com.nayibit.feature_phrases.domain.repositories.PhraseRepository
import com.nayibit.feature_phrases.domain.useCases.IsSpeakingUseCase
import com.nayibit.feature_phrases.domain.useCases.IsTtsAvailableUseCase
import com.nayibit.feature_phrases.domain.useCases.SpeakTextUseCase
import com.nayibit.feature_phrases.presentation.mappers.toPhrase
import com.nayibit.feature_phrases.presentation.mappers.toPhraseUi
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.CollapsedItem
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.DeletePhrase
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.DismissModal
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.ExpandItem
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.InsertPhrase
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.Navigation
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.ShowModal
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.ShowSnackbar
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.ShowToast
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.SpeakText
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.UpdatePhrase
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.UpdateTextExample
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.UpdateTextFirstPhrase
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.UpdateTextTraslation
import com.nayibit.feature_phrases.presentation.phraseScreen.PhraseUiEvent.UploadCurrentIndexCard
import com.nayibit.translation.domain.TranslationManager
import com.nayibit.tts.domain.TtsManager
import com.nayibit.utils.DataStoreKeys
import com.nayibit.utils.helpers.Result
import com.nayibit.utils.helpers.UiText
import com.nayibit.utils.helpers.UiText.DynamicString
import com.nayibit.utils.helpers.UiText.StringResource
import com.nayibit.utils.helpers.ValidateExampleResult
import com.nayibit.utils.helpers.normalizeSpaces
import com.nayibit.utils.helpers.onError
import com.nayibit.utils.helpers.onSuccess
import com.nayibit.utils.helpers.removeLonelySigns
import com.nayibit.utils.helpers.validateExample
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PhraseViewModel
@Inject constructor(
    private val repo: PhraseRepository,
    private val translationManager: TranslationManager,
    private val dataStore: GenericDataStore,
    private val ttsRepository: TtsManager,
    private val isTtsAvailableUseCase: IsTtsAvailableUseCase,
    private val isSpeakingUseCase: IsSpeakingUseCase,
    private val speakTextUseCase: SpeakTextUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val idDeck = savedStateHandle.get<Int>("idDeck") ?: -1

    private val _state = MutableStateFlow(
        PhraseStateUi(
            idDeck = idDeck
        )
    )
    val state: StateFlow<PhraseStateUi> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<PhraseUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var translationJob: Job? = null

    init {
        getAllPhrases(idDeck)
        loadLanguageCode()
        setupConfiguration()
    }

    private fun loadLanguageCode() {
        viewModelScope.launch {
            dataStore.getData(DataStoreKeys.SELECTED_LANGUAGE_CODE, "", String::class)
                .collect { code -> _state.update { it.copy(lngCode = code) } }
        }
    }

    fun onEvent(event: PhraseUiEvent) {
        when (event) {
            DismissModal -> {
                translationJob?.cancel()
                _state.value = _state.value.copy(
                    showModal = false,
                    isLoadingButton = false,
                    firstPhrase = "",
                    translation = "",
                    phraseToUpdate = null,
                    example = "",
                    isTranslating = false
                )
            }

            InsertPhrase -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(
                            bodyModal = BodyModalEnum.BODY_INSERT_PHRASE,
                            firstPhrase = _state.value.firstPhrase.removeLonelySigns(),
                            example = _state.value.example.removeLonelySigns()
                        )
                    }


                    val result = validateExample(_state.value.firstPhrase, _state.value.example)

                    when (result) {
                        ValidateExampleResult.IS_VALID -> {
                            insertPhrase(
                                Phrase(
                                    targetLanguage = _state.value.firstPhrase.normalizeSpaces(),
                                    translation = _state.value.translation.normalizeSpaces(),
                                    deckId = idDeck,
                                    example = _state.value.example.normalizeSpaces()
                                )
                            )
                        }

                        ValidateExampleResult.EXAMPLE_NOT_CONTAINS_PHRASE -> {
                            _eventFlow.emit(
                                ShowToast(UiText.StringResource(R.string.error_example_not_contains_phrase))
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
                    translationJob?.cancel()
                    if (event.text.isNotBlank()) {
                        translationJob = viewModelScope.launch {
                            delay(800L)
                            translatePhrase(event.text)
                        }
                    } else {
                        _state.update { it.copy(translation = "", isTranslating = false) }
                    }
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

            is UpdatePhrase -> {
                viewModelScope.launch {

                    _state.update {
                        it.copy(
                            bodyModal = BodyModalEnum.BODY_UPDATE_PHRASE,
                            firstPhrase = _state.value.firstPhrase.removeLonelySigns(),
                            example = _state.value.example.removeLonelySigns()
                        )
                    }

                    val result = validateExample(_state.value.firstPhrase, _state.value.example)

                    when (result) {
                        ValidateExampleResult.IS_VALID -> {
                            updatePhrase(
                                event.phraseUi.toPhrase()
                                    .copy(
                                        targetLanguage = _state.value.firstPhrase.normalizeSpaces(),
                                        translation = _state.value.translation.normalizeSpaces(),
                                        example = _state.value.example.normalizeSpaces(),
                                        deckId = idDeck
                                    )
                            )
                        }

                        ValidateExampleResult.EXAMPLE_NOT_CONTAINS_PHRASE -> {
                            _eventFlow.emit(
                                ShowToast(UiText.StringResource(R.string.error_example_not_contains_phrase))
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

                _state.update {
                    it.copy(
                        showModal = true,
                        bodyModal = event.type,
                        firstPhrase = event.phraseUi?.targetLanguage ?: "",
                        translation = event.phraseUi?.translation ?: "",
                        phraseToUpdate = event.phraseUi,
                        example = event.phraseUi?.example ?: ""
                    )
                }
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

            is SpeakText -> {
                speakTextUseCase(event.text, _state.value.lngCode)
            }
        }
    }

    private suspend fun translatePhrase(text: String) {
        val sourceCode = _state.value.lngCode
        if (sourceCode.isBlank()) return

        val deviceLang = Locale.getDefault().language
        val targetCode = if (deviceLang != sourceCode) deviceLang else "en"

        _state.update { it.copy(isTranslating = true) }
        translationManager.translate(text, sourceCode, targetCode).collect { result ->
            when (result) {
                is Result.Success -> _state.update {
                    it.copy(translation = result.data, isTranslating = false)
                }
                is Result.Error -> _state.update { it.copy(isTranslating = false) }
            }
        }
    }

    fun getAllPhrases(idDeck: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repo.getAllPhrasesByDeckId(idDeck)
                .collect { result ->
                    result.onSuccess { phrases ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                phrases = phrases.map { phrase ->
                                    phrase.toPhraseUi()
                                }.reversed()
                            )
                        }
                    }.onError {
                    }
                }
        }
    }

    fun insertPhrase(phrase: Phrase) {
        _state.update { it.copy(isLoadingButton = true) }
        viewModelScope.launch {
            repo.insert(phrase).onSuccess {
                _state.update {
                    it.copy(
                        isLoadingButton = false, showModal = false,
                        firstPhrase = "", translation = ""
                    )
                }
                _eventFlow.emit(ShowSnackbar(StringResource(R.string.label_phrase_inserted_success)))
            }.onError { error ->
                _eventFlow.emit(ShowSnackbar(DynamicString("$error")))
            }
        }
    }

    fun deletePhrase(id: Int) {
        viewModelScope.launch {
            _state.update { it.copy(isLoadingButton = true) }
            repo.delete(id).onSuccess {
                _state.update {
                    it.copy(
                        isLoadingButton = false,
                        showModal = false,
                        phraseToUpdate = null
                    )
                }
                _eventFlow.emit(ShowSnackbar(StringResource(R.string.phrase_delete_successfully)))
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoadingButton = false,
                        showModal = false,
                        phraseToUpdate = null
                    )
                }
                _eventFlow.emit(ShowSnackbar(DynamicString("$error")))
            }
        }
    }

    fun updatePhrase(phrase: Phrase) {
        _state.update { it.copy(isLoadingButton = true) }
        viewModelScope.launch {
            repo.update(phrase).onSuccess {
                _state.update {
                    it.copy(
                        isLoadingButton = false,
                        showModal = false,
                        phraseToUpdate = null
                    )
                }
                _eventFlow.emit(ShowSnackbar(StringResource(R.string.label_phrase_updated_success)))
            }.onError { error ->
                _state.update {
                    it.copy(
                        isLoadingButton = false,
                        showModal = false,
                        phraseToUpdate = null
                    )
                }
                _eventFlow.emit(ShowSnackbar(DynamicString("$error")))

            }
        }
    }


    private fun setupConfiguration() {
        viewModelScope.launch {
            isTtsAvailableUseCase().collect { ttsResult ->
                when (ttsResult) {
                    is Result.Error -> {
                        _state.update { it.copy(isTTsReady = false, isTtsSpeaking = false) }
                    }
                    is Result.Success -> {
                        if (ttsResult.data) {
                            _state.update { it.copy(isTTsReady = true) }
                            isProgressSpeaking()
                        } else {
                            _state.update { it.copy(isTtsSpeaking = false) }
                        }
                    }
                }
            }
        }
    }

    private suspend fun isProgressSpeaking() {
        isSpeakingUseCase().collect { progressSpeak ->
            if (progressSpeak)
                _state.update { it.copy(isTtsSpeaking = true) }
            else
                _state.update { it.copy(isTtsSpeaking = false) }
        }
    }

}
