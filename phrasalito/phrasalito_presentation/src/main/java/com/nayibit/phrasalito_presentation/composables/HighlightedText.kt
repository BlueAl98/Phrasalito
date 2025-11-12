package com.nayibit.phrasalito_presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.nayibit.common.util.isPunctuation

@Composable
fun HighlightedText(fullText: String, highlightWords: List<String>) {
    // Pre-normalize highlight words: remove non-letter chars and lowercase
    val normalizedHighlights = highlightWords
        .map { it.replace(Regex("[^\\p{L}]"), "").lowercase() }
        .toSet()

    val annotatedText = buildAnnotatedString {
        // split on any whitespace (handles multiple spaces / newlines / tabs)
        val tokens = fullText.split(Regex("\\s+"))

        tokens.forEachIndexed { index, token ->
            // Normalize token for comparison: remove non-letters, lowercase
            val clean = token.replace(Regex("[^\\p{L}]"), "").lowercase()

            if (clean.isNotEmpty() && normalizedHighlights.contains(clean)) {
                // highlight the whole token (word + punctuation). If you want to highlight only the letters
                // and keep punctuation unstyled, see the alternative version below.
                withStyle(
                    style = SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)
                ) {
                    append(token)
                }
            } else {
                append(token)
            }

            if (index < tokens.size - 1) append(" ")
        }
    }

    Text(text = annotatedText, style = MaterialTheme.typography.headlineSmall)
}

