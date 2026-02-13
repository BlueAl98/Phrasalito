package com.ie.feature_startscreen.presentation.startScreen


data class StartStateUi(
    val isLoading: Boolean = false,
    val checkPermissions: Boolean = false,
    val errorMessage: String? = null,
    val totalpages: Int = 2,
    val currentPage: Int = 0,
    val isFirstTime : Boolean = false,
)





