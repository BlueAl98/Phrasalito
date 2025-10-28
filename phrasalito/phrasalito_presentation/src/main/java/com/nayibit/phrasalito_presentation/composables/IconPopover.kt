package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd

@Composable
fun IconPopover(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    expandedState: Boolean = false,
    updateExpandedState: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    colorDisable : Color = Color.Gray,
    content: @Composable ColumnScope.() -> Unit = {},
) {
   var expanded by remember { mutableStateOf(expandedState) }


    Box  {
        // The trigger icon
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.Transparent,
                contentColor = primaryGradientEnd
            ),
            enabled = enabled,
            onClick = {
            expanded = !expanded
            updateExpandedState(expanded)
            onClick()
        }) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (!enabled) colorDisable else Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(primaryGradientEnd)
                .border(
                    width = 2.dp,
                    color = Color.White,
                    shape = RoundedCornerShape(16.dp)
                )

        ) {
            // Your content inside the popover
            content()
        }
    }
}


