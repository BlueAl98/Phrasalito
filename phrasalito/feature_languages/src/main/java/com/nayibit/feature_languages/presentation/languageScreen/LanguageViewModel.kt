package com.nayibit.feature_languages.presentation.languageScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.feature_languages.domain.model.LanguageStatus
import com.nayibit.feature_languages.domain.model.LanguageUi
import com.nayibit.feature_languages.domain.repository.LanguageRepository
import com.nayibit.utils.helpers.onError
import com.nayibit.utils.helpers.onSuccess
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
class LanguageViewModel @Inject constructor(
    private val repository: LanguageRepository
) : ViewModel() {

    private val _state = MutableStateFlow(LanguageStateUi())
    val state: StateFlow<LanguageStateUi> = _state.asStateFlow()

    private val _eventFlow = MutableSharedFlow<LanguageUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        loadLanguages()
    }

    fun onEvent(event: LanguageUiEvent) {
        when (event) {
            is LanguageUiEvent.SelectLanguage -> {
                viewModelScope.launch {
                    _eventFlow.emit(LanguageUiEvent.NavigateWithLanguage(event.language.code))
                }
            }
            is LanguageUiEvent.DownloadLanguage -> {
                viewModelScope.launch {
                    _eventFlow.emit(
                        LanguageUiEvent.ShowSnackbar("Descargando ${event.language.displayName}...")
                    )
                }
            }
            else -> {}
        }
    }

    private fun loadLanguages() {
        _state.update {
            it.copy(
                availableLanguages = listOf(
                    LanguageUi("en", "Inglés", "🇺🇸", LanguageStatus.AVAILABLE),
                    LanguageUi("it", "Italiano", "🇮🇹", LanguageStatus.AVAILABLE),
                    ),
                explorableLanguages = listOf(
                    LanguageUi("de", "Alemán", "🇩🇪", LanguageStatus.DOWNLOADABLE),
                    LanguageUi("ja", "Japonés", "🇯🇵", LanguageStatus.COMING_SOON)
                )
            )
        }
    }
}
