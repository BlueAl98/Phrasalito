package com.nayibit.phrasalito_presentation.utils

import com.nayibit.common.util.normalizeSpaces


fun validateExample(phrase: String, example: String = ""): ValidateExampleResult {
    val normalizedPhrase = phrase.trim().lowercase().normalizeSpaces()
    val normalizedExample = example.trim().lowercase().normalizeSpaces()

    if (example.isEmpty()) return ValidateExampleResult.IS_VALID

    val phraseWords = normalizedPhrase.split(" ")
    val exampleWords = normalizedExample.split(" ")

    // Check that all phrase words are present in example
    val containsAllWords = phraseWords.all { word ->
        exampleWords.contains(word)
    }
    if (!containsAllWords) return ValidateExampleResult.EXAMPLE_NOT_CONTAINS_PHRASE

    // Example must be longer than phrase (more context)
    if (normalizedExample.length <= normalizedPhrase.length + 3) {
        return ValidateExampleResult.EXAMPLE_IS_NOT_LONGER_THAN_PHRASE
    }

    return ValidateExampleResult.IS_VALID
}


fun exercisePhrase(targetPhrase: String, example: String): String {
    // Build normalized target set (remove non-letter/digits for matching)
    val targetSet = targetPhrase
        .lowercase()
        .replace(Regex("[^\\p{L}\\p{Nd}'’-]+"), " ")
        .split(Regex("\\s+"))
        .filter { it.isNotBlank() }
        .map { it.replace(Regex("[^\\p{L}\\p{Nd}]+"), "") } // "don't" -> "dont"
        .toSet()

    // Regex matches words containing letters/digits and common contractions/hyphens
    val wordRegex = Regex("""\b[\p{L}\p{Nd}'’-]+\b""")

    return wordRegex.replace(example) { mr ->
        val word = mr.value                            // preserves original case/punct inside token
        val cleaned = word.lowercase().replace(Regex("[^\\p{L}\\p{Nd}]+"), "")
        if (cleaned.isNotBlank() && cleaned in targetSet) {
            // replace each letter/digit with '_' but keep punctuation inside the token
            word.map { if (it.isLetterOrDigit()) '_' else it }.joinToString("")
        } else {
            word
        }
    }
}

fun calculateProgressPercentage(
    total: Int,
    completed: Int
): Float {
   return  completed.toFloat() / total.toFloat()
}


  enum class ValidateExampleResult {
      IS_VALID,
      EXAMPLE_NOT_CONTAINS_PHRASE,
      EXAMPLE_IS_NOT_LONGER_THAN_PHRASE
  }

fun List<String>.textWithoutSpecialCharacters(): String  {
    return this.joinToString(" ") {
        it.filter { it.isLetterOrDigit() }
    }
}

// Extension for a single String → returns a cleaned String
fun String.textWithoutSpecialCharacters(): String {
    return this.split(" ").joinToString(" ") {
        it.filter { it.isLetterOrDigit() }
    }
}

