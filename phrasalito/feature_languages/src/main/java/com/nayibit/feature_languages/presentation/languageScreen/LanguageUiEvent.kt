package com.nayibit.feature_languages.presentation.languageScreen

import com.nayibit.feature_languages.domain.model.LanguageUi

sealed class LanguageUiEvent {
    data class SelectLanguage(val language: LanguageUi) : LanguageUiEvent()
    data class DownloadLanguage(val language: LanguageUi) : LanguageUiEvent()
    data class ShowSnackbar(val message: String) : LanguageUiEvent()
    data class NavigateWithLanguage(val languageCode: String) : LanguageUiEvent()
}
