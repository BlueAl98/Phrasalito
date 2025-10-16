package com.nayibit.phrasalito_presentation.composables


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.NotificationsOff
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientStart

@Composable
fun SwitchBase(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    activeColor: Color = primaryGradientStart,      // ✅ Active background
    inactiveColor: Color = Color(0xFFCFD8DC),    // ✅ Inactive background
    iconColor: Color = primaryGradientStart         // ✅ Icon tint inside knob
) {
    // Animated track color
    val trackColor by animateColorAsState(
        targetValue = if (checked) activeColor else inactiveColor.copy(alpha = 0.6f),
        label = "trackColor"
    )

    // Animated knob position
    val knobOffset by animateDpAsState(
        targetValue = if (checked) 24.dp else 0.dp,
        label = "knobOffset"
    )

    // Animated icon type
    val icon = if (checked) Icons.Outlined.Notifications else Icons.Outlined.NotificationsOff

    Box(
        modifier = modifier
            .width(58.dp)
            .height(32.dp)
            .clickable { onCheckedChange(!checked) }
            .background(trackColor, CircleShape)
            .padding(horizontal = 4.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        // Moving knob with icon
        Box(
            modifier = Modifier
                .offset(x = knobOffset)
                .size(26.dp)
                .shadow(elevation = 3.dp, shape = CircleShape)
                .background(Color.White, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) activeColor else Color.Gray,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}
