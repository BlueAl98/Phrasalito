package com.nayibit.phrasalito_presentation.screens.startScreen

import java.util.Locale

data class StartStateUi(
    val isLoading: Boolean = false,
    val checkPermissions: Boolean = false,
    val errorMessage: String? = null,
    val totalpages: Int = 3,
    val currentPage: Int = 0,
    val isFirstTime : Boolean = false,
    val languages: List<Locale> = emptyList()
)

