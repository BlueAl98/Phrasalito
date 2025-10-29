package com.nayibit.phrasalito_presentation.composables


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesomeMotion
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUI
import com.nayibit.phrasalito_presentation.ui.theme.badgeComplete
import com.nayibit.phrasalito_presentation.ui.theme.badgeNew
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart
import com.nayibit.phrasalito_presentation.ui.theme.progressBackground
import kotlin.math.roundToInt


enum class DeckBadgeType {
    NONE,
    NEW,
    COMPLETE
}


@OptIn(ExperimentalWearMaterialApi::class)
@Composable
fun SwipeableDeckItem(
    modifier: Modifier = Modifier,
    deck: DeckUI,
    onEdit: (DeckUI) -> Unit,
    onDelete: (DeckUI) -> Unit,
    onClick: (DeckUI) -> Unit,
    isSwiped: Boolean = false,
    onSwipe: (Boolean) -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = if (isSwiped) 1 else 0)
    val sizePx = with(LocalDensity.current) { 120.dp.toPx() }
    val anchors = mapOf(0f to 0, -sizePx to 1) // 0 = default, 1 = swiped left

    // 🔹 User interaction — detect manual swipe change
    LaunchedEffect(swipeableState.currentValue) {
        val currentlySwiped = swipeableState.currentValue == 1
        if (currentlySwiped != isSwiped) {
            onSwipe(currentlySwiped)
        }
    }

    // 🔹 React to parent updates
    LaunchedEffect(isSwiped) {
        if (isSwiped && swipeableState.currentValue != 1) {
            swipeableState.animateTo(1)
        } else if (!isSwiped && swipeableState.currentValue != 0) {
            swipeableState.animateTo(0)
        }
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.3f) },
                orientation = Orientation.Horizontal
            )
    ) {
        // Background actions
        Row(
            modifier
                .matchParentSize()
                .padding(3.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onDelete(deck) }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.Red)
            }
            Spacer(Modifier.width(12.dp))
            IconButton(onClick = { onEdit(deck) }) {
                Icon(Icons.Default.Edit, contentDescription = "Edit", tint = primaryGradientStart)
            }
        }

        // Foreground card
        CardDeck(
            modifier = Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .clickable { onClick(deck) }
                .padding(10.dp)
            ,
            title = deck.name,
            currentCards = deck.numCards,
            totalCards = deck.maxCards,
            onClickToTest = { onClick(deck) },
            isNotified = deck.isNotified,
            bottomRightText = deck.selectedLanguage?.language
        )
    }

}



@Composable
fun CardDeck(
    modifier: Modifier = Modifier,
    title: String,
    currentCards: Int,
    totalCards: Int,
    icon: ImageVector = Icons.Default.AutoAwesomeMotion,
    badgeType: DeckBadgeType = DeckBadgeType.NONE,
    onClickToTest: () -> Unit,
    isNotified: Boolean = false,
    bottomRightText: String? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 40.dp,
        animationSpec = tween(durationMillis = 150),
        label = "elevation"
    )

    val offsetY by animateDpAsState(
        targetValue = if (isPressed) (-2).dp else (-4).dp,
        animationSpec = tween(durationMillis = 150),
        label = "offsetY"
    )

    val progress = if (totalCards > 0) currentCards.toFloat() / totalCards.toFloat() else 0f

    Card(
        modifier = modifier
            .fillMaxWidth()
            .offset(y = offsetY)
            .shadow(
                elevation = elevation,
                shape = RoundedCornerShape(20.dp),
                spotColor = MaterialTheme.colorScheme.inversePrimary,
                ambientColor = MaterialTheme.colorScheme.inversePrimary
            )

            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickToTest
            )
        ,
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)

        ) {
            // Left gradient border
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .fillMaxHeight()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                primaryGradientStart,
                                primaryGradientEnd
                            )
                        )
                    )
                    .align(Alignment.CenterStart)
            )

            // Existing badge (unchanged)
            if (badgeType != DeckBadgeType.NONE) {
                DeckBadge(
                    type = badgeType,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }

                Icon(
                    imageVector = if (isNotified) Icons.Outlined.Notifications else Icons.Outlined.NotificationsOff,
                    contentDescription = if (isNotified) "Notifications On" else "Notifications Off",
                    tint = if (isNotified) primaryGradientStart else Color.Gray.copy(alpha = 0.7f),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 12.dp, end = 12.dp)
                        .size(22.dp))


            // Card content
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon
                DeckIcon(icon = icon)

                // Content
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = title,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        lineHeight = 24.sp
                    )

                    DeckProgress(
                        currentCards = currentCards,
                        totalCards = totalCards,
                        progress = progress
                    )
                    Spacer(modifier.size(3.dp))
                }
            }

            // 🆕 Small text bottom-right
            if (!bottomRightText.isNullOrEmpty()) {
                Text(
                    text = bottomRightText,
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(top = 5.dp, end = 12.dp, bottom = 8.dp)
                )
            }
        }
    }
}



@Composable
private fun DeckIcon(
    icon: ImageVector
) {
    Box(
        modifier = Modifier
            .size(60.dp)
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(16.dp),
                spotColor = primaryGradientStart.copy(alpha = 0.3f)
            )
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryGradientStart,
                        primaryGradientEnd
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(32.dp)
        )
    }
}

@Composable
private fun DeckProgress(
    currentCards: Int,
    totalCards: Int,
    progress: Float
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "$currentCards / $totalCards",
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
        )

        // Progress bar
        Box(
            modifier = Modifier
                .weight(1f)
                .height(6.dp)
                .background(
                    color = progressBackground,
                    shape = RoundedCornerShape(3.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(progress)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryGradientStart,
                                primaryGradientEnd
                            )
                        ),
                        shape = RoundedCornerShape(3.dp)
                    )
            )
        }
    }
}


@Composable
private fun DeckBadge(
    type: DeckBadgeType,
    modifier: Modifier = Modifier
) {
    val (text, brush) = when (type) {
        DeckBadgeType.NEW -> "NEW" to badgeNew
        DeckBadgeType.COMPLETE -> "COMPLETE" to badgeComplete
        DeckBadgeType.NONE -> return
    }

    Box(
        modifier = modifier
            .background(
                brush = brush,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        Text(
            text = text,
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.White,
            letterSpacing = 0.5.sp
        )
    }
}

