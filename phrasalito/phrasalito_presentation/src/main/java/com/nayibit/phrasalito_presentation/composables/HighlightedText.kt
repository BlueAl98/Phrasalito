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
    val annotatedText = buildAnnotatedString {
        val words = fullText.split(" ")
        words.forEachIndexed { index, word ->
            // Remove punctuation for comparison (but keep it for display)
            val cleanWord = word.trim { it.isWhitespace() || it.isPunctuation() }

            if (highlightWords.any { it.equals(cleanWord, ignoreCase = true) }) {
                withStyle(
                    style = SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)
                ) {
                    append(word)
                }
            } else {
                append(word)
            }

            if (index < words.size - 1) append(" ")
        }
    }

    Text(text = annotatedText, style = MaterialTheme.typography.headlineSmall)
}

