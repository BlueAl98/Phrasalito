package com.nayibit.phrasalito_presentation.composables


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/*
@Composable
fun CardDeck(
    title: String,
    maxCards: Int = 0,
    currentCards: Int = 0,
    modifier: Modifier = Modifier,
    icon: ImageVector = Icons.Default.ArrowForward,
    onClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 4.dp,
    iconTint: Color = MaterialTheme.colorScheme.tertiary
) {
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(cornerRadius),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp), // 👈 common padding for all children
            verticalArrangement = Arrangement.spacedBy(8.dp) // 👈 equal spacing between row & text
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically // 👈 centers icon & text vertically
            ) {
                Text(
                    text = "Nombre deck: $title",
                    style = MaterialTheme.typography.titleMedium
                )

                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint
                )
            }

            Text(
                text = "Num cards $currentCards - $maxCards",
                style = MaterialTheme.typography.titleMedium
            )
        }

    }
}
*/


@Composable
fun CardDeck(
    title: String,
    currentCards: Int = 0,
    maxCards: Int = 0,
    modifier: Modifier = Modifier,
    primaryIcon: ImageVector = Icons.Default.ArrowForward,
    secondaryIcon: ImageVector? = null, // optional second icon
    onCardClick: () -> Unit = {},
    onPrimaryIconClick: () -> Unit = {},
    onSecondaryIconClick: () -> Unit = {},
    backgroundColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color = MaterialTheme.colorScheme.outline,
    cornerRadius: Dp = 16.dp,
    elevation: Dp = 4.dp,
    iconTint: Color = MaterialTheme.colorScheme.primary
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(cornerRadius),
        border = BorderStroke(1.dp, borderColor),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = elevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onCardClick() }
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Left content: title + card info
            Column(
                modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Cards: $currentCards / $maxCards",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Right content: icons
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                secondaryIcon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { onSecondaryIconClick() }
                    )
                }

                Spacer(modifier = Modifier.size(10.dp))

                Icon(
                    imageVector = primaryIcon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier
                        .size(30.dp)
                        .clickable { onPrimaryIconClick() }
                )
            }
        }
    }
}

@Preview
@Composable
private fun Cardpreview() {
    CardDeck(
        title = "My Deck IS THE BEST DECK FOR LEARN ENGLISH",
        currentCards = 10,
        maxCards = 20,
        primaryIcon = Icons.Default.ArrowForward,
        secondaryIcon = Icons.Default.Info,
        onCardClick = { /* Navigate to deck details */ },
        onPrimaryIconClick = { /* Start exercise */ },
        onSecondaryIconClick = { /* Show info */ }
    )

}