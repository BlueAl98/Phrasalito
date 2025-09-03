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
    val targetWords = targetPhrase.lowercase().split(" ").textWithoutSpecialCharacters()

    val convertString = example.split(" ").map { word ->
        // Remove punctuation like commas, periods, etc.
        val cleanWord = word.filter { it.isLetterOrDigit() }

        if (targetWords.contains(cleanWord.lowercase())) {
            "_".repeat(cleanWord.length) + word.takeLastWhile { !it.isLetterOrDigit() }
        } else {
            word
        }
    }

    return convertString.joinToString(" ")
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

