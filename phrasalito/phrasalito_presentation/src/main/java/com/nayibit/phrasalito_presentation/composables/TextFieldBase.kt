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

/*
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
                  maxChar: Int = 30,
                  isError: Boolean = false,
                  enabled: Boolean = true,
                  shape: Shape = RoundedCornerShape(8.dp),
                  textStyle: TextStyle = LocalTextStyle.current,
                  keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                  visualTransformation: VisualTransformation = VisualTransformation.None,
    // Dynamic colors: pass colors you want (use MaterialTheme colors or custom)
                  focusedColor: Color = MaterialTheme.colorScheme.primary,
                  unfocusedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
                  errorColor: Color = MaterialTheme.colorScheme.error
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
        onValueChange = {newText ->
            // Apply your cleaning functions first
            val cleaned = newText.allowOnlyLettersAndSigns().cleanRepeatedSigns()
            // Limit characters
            if (cleaned.length <= maxChar) {
                onValueChange(cleaned)
            }
        },
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
        colors = colors,

    )
}
*/
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
    maxChar: Int = 30,
    showCharCounter: Boolean = false,
    textRestriction: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(8.dp),
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    focusedColor: Color = MaterialTheme.colorScheme.primary,
    unfocusedColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    errorColor: Color = MaterialTheme.colorScheme.error
) {

    // 👇 Convert your plain text to a TextFieldValue that remembers cursor position
    var textFieldValue by remember(value) {
        mutableStateOf(TextFieldValue(value, selection = androidx.compose.ui.text.TextRange(value.length)))
    }

    // When external text changes, update the internal state if necessary
    if (textFieldValue.text != value) {
        textFieldValue = textFieldValue.copy(
            text = value,
            selection = androidx.compose.ui.text.TextRange(value.length)
        )
    }


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

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = textFieldValue,
            onValueChange = { newText ->
                textFieldValue = newText
                val cleaned = if (textRestriction)textFieldValue.text.allowOnlyLettersAndSigns().cleanRepeatedSigns() else textFieldValue.text
                if (cleaned.length <= maxChar) onValueChange(cleaned)
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