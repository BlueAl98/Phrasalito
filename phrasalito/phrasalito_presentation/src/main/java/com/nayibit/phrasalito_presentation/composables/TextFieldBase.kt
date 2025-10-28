package com.nayibit.phrasalito_presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.nayibit.common.util.allowOnlyLettersAndSigns
import com.nayibit.common.util.cleanRepeatedSigns
import com.nayibit.phrasalito_presentation.ui.theme.primaryGradientEnd

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
    maxChar: Int = 100,
    showCharCounter: Boolean = false,
    textRestriction: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusedColor: Color = primaryGradientEnd,
    unfocusedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    errorColor: Color = MaterialTheme.colorScheme.error
) {

    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = value)) }

    LaunchedEffect(value) {
        if (value != textFieldValue.text) {
            val newSelection = textFieldValue.selection
            val start = newSelection.start.coerceIn(0, value.length)
            val end = newSelection.end.coerceIn(0, value.length)
            textFieldValue = textFieldValue.copy(
                text = value,
                selection = androidx.compose.ui.text.TextRange(start, end)
            )
        }
    }


    val colors: TextFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurface,
        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
        errorTextColor = MaterialTheme.colorScheme.onSurface,
        focusedContainerColor = Color.Transparent,
        unfocusedContainerColor = Color.Transparent,
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        focusedIndicatorColor = focusedColor,
        unfocusedIndicatorColor = unfocusedColor,
        unfocusedLabelColor = unfocusedColor,
        focusedLabelColor = focusedColor,
        disabledIndicatorColor = if (isError) Color.Red else Color.Transparent
    )

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newText ->
                var cleaned = newText.text
                if (textRestriction) {
                    cleaned = cleaned.allowOnlyLettersAndSigns().cleanRepeatedSigns()
                }

                // Notify parent if cleaned changed text
                if (cleaned.length <= maxChar) {
                    // Update internal state
                   textFieldValue = newText.copy(text = cleaned)
                    onValueChange(cleaned)
                }
            },
            modifier = Modifier.fillMaxWidth(),
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
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            colors = colors
        )

        // 👇 Animated visibility for counter
        AnimatedVisibility(
            visible = showCharCounter && value.isNotEmpty(),
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 12.dp, top = 16.dp, bottom = 2.dp)
        ) {
            Text(
                text = "${value.length} / $maxChar",
                style = MaterialTheme.typography.labelSmall,
                color = if (value.length >= maxChar) errorColor else unfocusedColor
            )
        }
    }
}