package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ButtonBase(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    disabledBackgroundColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.30f),
    disabledContentColor: Color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
    shapeRadius: Int = 8,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge
) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor,
            disabledContainerColor = disabledBackgroundColor,
            disabledContentColor = disabledContentColor
        ),
        shape = RoundedCornerShape(shapeRadius.dp)
    ) {
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.size(20.dp),
                color = backgroundColor,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                style = textStyle
            )
        }
    }
}

@Composable
@Preview
fun ButtonBasePreview() {
    Column {
        ButtonBase(
            text = "Button",
            onClick = {},
            loading = true
        )

        ButtonBase(
            text = "Button 2",
            onClick = {},
            loading = false
        )

    }

}
