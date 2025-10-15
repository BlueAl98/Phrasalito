package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.nayibit.phrasalito_presentation.model.Language


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageDropdownMenu(
    languages: List<Language>,
    selectedLanguage: Language?,
    onLanguageSelected: (Language) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Select Language",
    enabled: Boolean = true,
    showAlias: Boolean = true
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it && enabled },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedLanguage?.language ?: "",
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                unfocusedBorderColor = Color.Gray,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                unfocusedLabelColor = Color.Gray
            ),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            enabled = enabled
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.exposedDropdownSize()
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text = {
                        Column {
                            Text(
                                text = language.language,
                                style = MaterialTheme.typography.bodyLarge,
                            )
                            if (showAlias && language.alias.isNotEmpty()) {
                                Text(
                                    text = language.alias,
                                    style = MaterialTheme.typography.bodySmall,
                                )
                            }
                        }
                    },
                    onClick = {
                        onLanguageSelected(language)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}