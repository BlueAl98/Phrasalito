package com.nayibit.phrasalito_presentation.screens.startScreen

data class StartStateUi(
    val isLoading: Boolean = false,
    val checkPermissions: Boolean = false,
    val hasPermission: Boolean = false,
    val errorMessage: String? = null

    )