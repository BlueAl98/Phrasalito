package com.nayibit.feature_categories.presentation.categoryScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.feature_categories.presentation.composables.AnimatedCategoryCard
import com.nayibit.feature_categories.presentation.composables.DialogCategory
import com.nayibit.feature_categories.presentation.model.TypeModal
import com.nayibit.utils.ui.composables.LoadingScreen
import com.nayibit.utils.ui.theme.learningColors
import kotlinx.coroutines.flow.Flow

@Composable
fun CategoryScreen(
    modifier: Modifier = Modifier,
    state: CategoryStateUi,
    eventFlow: Flow<CategoryUiEvent>,
    onEvent: (CategoryUiEvent) -> Unit,
    navigation: (Int) -> Unit
) {

    val context = LocalContext.current
    val colors = learningColors(textPrimary = MaterialTheme.colorScheme.inversePrimary)

    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is CategoryUiEvent.Navigate -> {
                    navigation(event.id)
                }

                is CategoryUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onEvent(CategoryUiEvent.ShowDialog(true)) },
                containerColor = Color(0xFF0047AB)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", tint = Color.White)
            }
        }
    ) { padding ->

       if (state.categories.isEmpty()){
           Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center){
               Text(text = "No hay categorias")
           }
       }else
         Column(
            modifier = modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(20.dp)
                .padding(padding)
        ) {

            Text(
                text = "¿Listo para aprender?",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.inversePrimary
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text = "Selecciona una categoria para empezar",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.8f)
            )

            Spacer(Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(18.dp),
                horizontalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                items(state.categories, key = { it.id }) { category ->
                    AnimatedCategoryCard(
                        category = category,
                        colors = colors,
                        index = category.id,
                        flippedCard = { onEvent(CategoryUiEvent.FlipCard(category,it)) },
                        isFlipped = category.isFlipped,
                        onClickItem = {
                            onEvent(CategoryUiEvent.Navigate(category.id))
                        },
                        onEdit = {
                            onEvent(CategoryUiEvent.ShowDialog(true, TypeModal.UPDATE, category))
                           },
                        onDelete = {
                            onEvent(CategoryUiEvent.ShowDialog(true, TypeModal.DELETE, category))
                        }
                    )
                }
            }
        }
        DialogCategory(
            colorButtons = colors.primary,
            showDialog = state.showDialog,
            state = state,
            onEvent = onEvent,
            currentCategory = state.currentCategory,
            typeModal = state.typeModal
        )

        if (state.isLoading)
            LoadingScreen()
    }
}




