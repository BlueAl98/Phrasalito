package com.nayibit.feature_languages.presentation.languageScreen

import com.nayibit.feature_languages.domain.model.LanguageUi

data class LanguageStateUi(
    val availableLanguages: List<LanguageUi> = emptyList(),
    val explorableLanguages: List<LanguageUi> = emptyList(),
    val isLoading: Boolean = false,
    val showErrorDialog: Boolean = false,
    val errorMessage: String? = null,
    val downloadingCode: String? = null
)
