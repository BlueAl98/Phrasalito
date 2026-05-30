package com.nayibit.feature_languages.presentation.languageScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.datastore.data.GenericDataStore
import com.nayibit.feature_languages.data.remote.mapper.toUi
import com.nayibit.feature_languages.domain.usecase.DownloadLanguageUseCase
import com.nayibit.feature_languages.domain.usecase.GetLanguagesUseCase
import com.nayibit.utils.DataStoreKeys
import com.nayibit.network.error.NetworkError
import com.nayibit.translation.domain.TranslationManager
import com.nayibit.translation.domain.model.ModelDownloadState
import com.nayibit.utils.helpers.Resource
import com.nayibit.utils.helpers.Result
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
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class LanguageViewModel @Inject constructor(
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val downloadLanguageUseCase: DownloadLanguageUseCase,
    private val translationManager: TranslationManager,
    private val dataStore: GenericDataStore
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
            is LanguageUiEvent.SelectLanguage -> viewModelScope.launch {
                dataStore.saveMultipleData(
                    mapOf(
                        DataStoreKeys.SELECTED_LANGUAGE_ID to event.language.id,
                        DataStoreKeys.SELECTED_LANGUAGE_CODE to event.language.code
                    )
                )
                _eventFlow.emit(LanguageUiEvent.NavigateWithLanguage(event.language.code))
            }
            is LanguageUiEvent.DownloadLanguage -> {}/*downloadLanguage(event.language.code)*/
            LanguageUiEvent.DismissErrorDialog -> _state.update { it.copy(showErrorDialog = false) }
            LanguageUiEvent.RetryLoad -> {
                _state.update { it.copy(showErrorDialog = false) }
                loadLanguages()
            }
            else -> {}
        }
    }

   /* private fun downloadLanguage(code: String) {
        viewModelScope.launch {
            _state.update { it.copy(downloadingCode = code) }
            downloadLanguageUseCase(code).collect { resource ->
                when {
                    resource is Resource.Success && resource.data == ModelDownloadState.Downloaded -> {
                        _state.update { it.copy(downloadingCode = null) }
                        preDownloadTargetModel(code)
                        loadLanguages()
                    }
                    resource is Resource.Success && resource.data is ModelDownloadState.Error -> {
                        val msg = (resource.data as ModelDownloadState.Error).message
                        _state.update { it.copy(downloadingCode = null) }
                        _eventFlow.emit(LanguageUiEvent.ShowSnackbar(msg))
                    }
                    resource is Resource.Error -> {
                        _state.update { it.copy(downloadingCode = null) }
                        _eventFlow.emit(LanguageUiEvent.ShowSnackbar(resource.message))
                    }
                }
            }
        }
    }*/

    private fun preDownloadTargetModel(sourceCode: String) {
        val targetCode = Locale.getDefault().language
        if (targetCode == sourceCode) return
        viewModelScope.launch {
            translationManager.downloadModel(targetCode).collect {}
        }
    }

    private fun loadLanguages() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getLanguagesUseCase()
                .collect { result ->
                   result.onSuccess { languages ->
                       _state.update {
                           it.copy(
                               isLoading = false,
                               availableLanguages = languages.filter { l -> l.isDownload }.map { l -> l.toUi() },
                               explorableLanguages = languages.filterNot { l -> l.isDownload }.map { l -> l.toUi() }
                           )
                       }
                   }.onError { error ->
                       _state.update {
                           it.copy(
                               isLoading = false,
                               showErrorDialog = true,
                               errorMessage = error.toMessage()
                           )
                       }
                   }
                }
        }
    }

    private fun NetworkError.toMessage(): String = when (this) {
        is NetworkError.NoInternet -> "Sin conexión a internet. Verifica tu conexión e intenta de nuevo."
        is NetworkError.Timeout -> "Tiempo de espera agotado. Intenta de nuevo."
        is NetworkError.Http -> message
        NetworkError.Unknown -> "Ocurrió un error inesperado."
    }
}
