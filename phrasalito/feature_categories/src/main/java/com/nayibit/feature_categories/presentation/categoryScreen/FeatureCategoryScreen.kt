package com.nayibit.feature_categories.presentation.categoryScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun FeatureCategoryScreen(
    navigation: () -> Unit
){
    val viewModel: CategoryViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    CategoryScreen(
        state = state,
        eventFlow = viewModel.eventFlow,
        onEvent = viewModel::onEvent
    ) {
       navigation()
    }
}