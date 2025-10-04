package com.nayibit.phrasalito_presentation.composables


import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.ui.theme.badgeComplete
import com.nayibit.phrasalito_presentation.ui.theme.badgeNew
import com.nayibit.phrasalito_presentation.ui.theme.cardBackground
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart
import com.nayibit.phrasalito_presentation.ui.theme.progressBackground
import com.nayibit.phrasalito_presentation.ui.theme.textPrimary
import com.nayibit.phrasalito_presentation.ui.theme.textSecondary


enum class DeckBadgeType {
    NONE,
    NEW,
    COMPLETE
}


@Composable
fun CardDeck(
    title: String,
    currentCards: Int,
    totalCards: Int,
    icon: ImageVector = Icons.Default.Star,
    badgeType: DeckBadgeType = DeckBadgeType.NONE,
    onClick: () -> Unit,
    onClickToTest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val elevation by animateDpAsState(
        targetValue = if (isPressed) 2.dp else 8.dp,
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
                spotColor = primaryGradientStart.copy(alpha = 0.3f)
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClickToTest
            ),
        shape = RoundedCornerShape(20.dp)

    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
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

            // Badge
            if (badgeType != DeckBadgeType.NONE) {
                DeckBadge(
                    type = badgeType,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                )
            }

            // Card content
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Icon
                DeckIcon(
                    icon = icon
                )

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
                }

                // Arrow
                DeckArrow(onClick = onClick)
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
private fun DeckArrow(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(32.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        primaryGradientStart.copy(alpha = 0.15f),
                        primaryGradientEnd.copy(alpha = 0.15f)
                    )
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable{onClick()}
        ,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Open deck",
            tint = primaryGradientStart,
            modifier = Modifier.size(16.dp)
        )
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

