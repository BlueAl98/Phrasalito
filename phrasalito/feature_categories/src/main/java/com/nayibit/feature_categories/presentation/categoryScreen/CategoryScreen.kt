package com.nayibit.feature_categories.presentation.categoryScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.feature_categories.presentation.composables.AnimatedCategoryCard
import com.nayibit.feature_categories.presentation.model.LearningColors
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
    val colors = learningColors(Color(0xFF0047AB), MaterialTheme.colorScheme.inversePrimary)

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


    Column(
        modifier = modifier
            .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
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
            items(state.categories) { category ->
                AnimatedCategoryCard(
                    modifier = Modifier.clickable{
                        onEvent(CategoryUiEvent.Navigate(category.id))
                    },
                    category = category,
                    colors = colors,
                    index = category.id
                )
            }
        }
    }
}




fun learningColors(primary: Color, textPrimary: Color = Color.White) = LearningColors(
    background = primary.copy(alpha = 0.08f),
    card = primary.copy(alpha = 0.14f),
    cardBorder = primary.copy(alpha = 0.25f),
    primary = primary,
    textPrimary = textPrimary,
    textSecondary = Color(0xFFB0B8C9),
    progressTrack = primary.copy(alpha = 0.18f),
    badge = primary.copy(alpha = 0.22f)
)