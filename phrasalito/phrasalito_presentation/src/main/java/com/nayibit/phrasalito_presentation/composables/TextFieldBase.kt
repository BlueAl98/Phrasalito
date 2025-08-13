package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Composable
fun TextFieldBase(
                  value: String,
                  onValueChange: (String) -> Unit,
                  modifier: Modifier = Modifier,
                  label: String? = null,
                  placeholder: String? = null,
                  leadingIcon: (@Composable (() -> Unit))? = null,
                  trailingIcon: (@Composable (() -> Unit))? = null,
                  singleLine: Boolean = true,
                  maxLines: Int = 1,
                  isError: Boolean = false,
                  enabled: Boolean = true,
                  shape: Shape = RoundedCornerShape(8.dp),
                  textStyle: TextStyle = LocalTextStyle.current,
                  keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                  visualTransformation: VisualTransformation = VisualTransformation.None,
    // Dynamic colors: pass colors you want (use MaterialTheme colors or custom)
                  focusedColor: Color = MaterialTheme.colorScheme.primary,
                  unfocusedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
                  errorColor: Color = MaterialTheme.colorScheme.error,
) {
    val colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        errorTextColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = Color.Transparent,
        focusedIndicatorColor = focusedColor,
        unfocusedIndicatorColor = unfocusedColor,
        unfocusedLabelColor = unfocusedColor,
        focusedLabelColor = focusedColor
    )

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth(),
        label = label?.let { { Text(it) } },
        placeholder = placeholder?.let { { Text(it) } },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        singleLine = singleLine,
        maxLines = maxLines,
        isError = isError,
        enabled = enabled,
        shape = shape,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        colors = colors
    )
}

@Composable
@Preview
fun TextFieldBasePreview(){

    TextFieldBase(
        value = "",
        onValueChange = {},
        label = "Custom color",
        placeholder = "Try custom color"
    )
}