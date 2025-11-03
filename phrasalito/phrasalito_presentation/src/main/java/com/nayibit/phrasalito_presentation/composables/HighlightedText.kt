package com.nayibit.phrasalito_presentation.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.nayibit.phrasalito_presentation.utils.textWithoutSpecialCharacters

@Composable
fun HighlightedText(fullText: String, highlightWords: List<String>) {

    val annotatedText = buildAnnotatedString {
        val words = fullText.split(" ")
        words.forEachIndexed { index, word ->
            if (highlightWords.contains(word)) {
                withStyle(
                    style = SpanStyle(color = Color.Yellow, fontWeight = FontWeight.Bold)
                ) {
                    append(word)
                }
            } else {
                append(word)
            }
            if (index < words.size - 1) append(" ") // re-add space
        }
    }

    Text(text = annotatedText, style = MaterialTheme.typography.headlineSmall)
}
