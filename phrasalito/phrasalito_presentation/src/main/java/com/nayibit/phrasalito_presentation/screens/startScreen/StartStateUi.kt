package com.nayibit.phrasalito_presentation.screens.startScreen

import com.nayibit.phrasalito_presentation.model.Language


data class StartStateUi(
    val isLoading: Boolean = false,
    val checkPermissions: Boolean = false,
    val errorMessage: String? = null,
    val totalpages: Int = 3,
    val currentPage: Int = 0,
    val isFirstTime : Boolean = false,
    val languages: List<Language> = emptyList(),
    val currentLanguage: Language? = null,
    val languageListScrollIndex: Int = 0,
    val languageListScrollOffset: Int = 0
)





