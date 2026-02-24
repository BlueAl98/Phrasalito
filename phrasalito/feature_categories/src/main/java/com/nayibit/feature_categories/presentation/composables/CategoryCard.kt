package com.nayibit.feature_categories.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearWavyProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.feature_categories.presentation.model.CategoryUi
import com.nayibit.feature_categories.presentation.model.LearningColors
import kotlinx.coroutines.delay


@Composable
fun AnimatedCategoryCard(
    modifier: Modifier = Modifier,
    category: CategoryUi,
    colors: LearningColors,
    onEdit: () -> Unit = {},
    onDelete: () -> Unit = {},
    onClickItem: () -> Unit,
    isFlipped: Boolean = false,
    flippedCard : (Boolean) -> Unit = {},
    index: Int
) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(index * 90L) // stagger effect
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(500)
        ) + slideInVertically(
            animationSpec = tween(500),
            initialOffsetY = { it / 2 }
        ) + scaleIn(
            initialScale = 0.9f,
            animationSpec = tween(500)
        )
    ) {
       FlippableCategoryCard(
           modifier = modifier,
           flipped = isFlipped,
           flippedCard = flippedCard,
           topic = category,
           colors = colors,
           onEdit = onEdit,
           onDelete = onDelete,
           onClickItem = onClickItem
       )
    }
}


@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CategoryCard(
    modifier: Modifier,
    topic: CategoryUi,
    colors: LearningColors
){
    val percent = (topic.progress * 100).toInt()

    Card(
        modifier = modifier.sizeIn(minHeight = 222.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.card
        ),
        border = BorderStroke(1.dp, colors.cardBorder),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier.padding(18.dp)
        ) {

            Column {

                Box(
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .background(colors.cardBorder),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = topic.icon,
                        contentDescription = null,
                        tint = colors.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = topic.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colors.textPrimary
                )

                Text(
                    text = topic.subtitle,
                    fontSize = 14.sp,
                    color = colors.primary
                )

                Spacer(Modifier.height(14.dp))


                LinearWavyProgressIndicator(
                    progress = {topic.progress},
                    color = colors.primary,
                    trackColor = colors.progressTrack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(50))
                )
            }

            ProgressBadge(percent, colors, Modifier.align(Alignment.TopEnd))
        }
    }
}


@Composable
fun FlippableCategoryCard(
    modifier: Modifier = Modifier,
    topic: CategoryUi,
    colors: LearningColors,
    flipped: Boolean =false,
    flippedCard : (Boolean) -> Unit = {},
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClickItem: () -> Unit
) {
  //  var flipped by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (flipped) 180f else 0f,
        label = "card_rotation"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                rotationY = rotation
                cameraDistance = 12f * density
            }
            .combinedClickable(
                onClick = {onClickItem()},
                onLongClick = { flippedCard(!flipped) }
            )
    ) {

        if (rotation <= 90f) {
            CategoryFront(topic, colors)
        } else {
            Box(
                modifier = Modifier.graphicsLayer {
                    rotationY = 180f
                }
            ) {
                CategoryBack(
                    onEdit = onEdit,
                    onDelete = onDelete,
                    colors = colors
                )
            }
        }
    }
}


@Composable
fun CategoryFront(topic: CategoryUi, colors: LearningColors) {
    CategoryCard(
        modifier = Modifier.fillMaxWidth(),
        topic = topic,
        colors = colors
    )
}

@Composable
fun CategoryBack(
    modifier : Modifier = Modifier,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    colors: LearningColors
) {
    Card(
        modifier = modifier.height( 222.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = colors.card
        ),
        border = BorderStroke(1.dp, colors.cardBorder),
        shape = RoundedCornerShape(20.dp)
    ) {

        Box(
            modifier = Modifier.padding(18.dp)
        ) {

            Row(Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
                ) {

            IconButton(onClick = {
                onEdit()
            }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    tint = colors.primary,
                    modifier = Modifier.size(30.dp)

                )
            }

             IconButton(onClick = {onDelete()}) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red,
                    modifier = Modifier.size(30.dp)

                )
            }
        }
    }
    }
}

@Composable
fun ProgressBadge(
    percent: Int,
    colors: LearningColors,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(50))
            .background(colors.badge)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = "$percent%",
            color = colors.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp
        )
    }
}