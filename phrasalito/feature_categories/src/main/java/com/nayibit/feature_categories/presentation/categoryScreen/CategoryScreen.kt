package com.nayibit.feature_categories.presentation.categoryScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    state: CategoryStateUi,
    eventFlow: Flow<CategoryUiEvent>,
    onEvent: (CategoryUiEvent) -> Unit,
    navigation: () -> Unit
) {

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is CategoryUiEvent.Navigate -> {
                    navigation()
                }

                is CategoryUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    Box(Modifier.fillMaxSize().background(Color.Blue))

}


