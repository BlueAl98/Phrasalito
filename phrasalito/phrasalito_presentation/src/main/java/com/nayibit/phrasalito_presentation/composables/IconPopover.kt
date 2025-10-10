package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IconPopover(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    expandedState: Boolean = false,
    updateExpandedState: (Boolean) -> Unit = {},
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit = {},
) {
   var expanded by remember { mutableStateOf(expandedState) }


    Box  {
        // The trigger icon
        IconButton(
            enabled = enabled,
            onClick = {
            expanded = !expanded
            updateExpandedState(expanded)
            onClick()
        }) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .background(
                    color = Color.Transparent,
                    shape = RoundedCornerShape(12.dp) // Rounded corners
                )
                .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
        ) {
            // Your content inside the popover
            content()
        }
    }
}


