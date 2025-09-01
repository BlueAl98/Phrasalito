package com.nayibit.phrasalito_presentation.utils

import android.util.Log
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

fun exercisePhrase() {
    val example = "dont slag me off"

    val phrase = listOf("slag", "off")

    var convertString = example.split(" ").toMutableList()

    convertString.removeIf { phrase.contains(it)}


}

  enum class ValidateExampleResult {
      IS_VALID,
      EXAMPLE_NOT_CONTAINS_PHRASE,
      EXAMPLE_IS_NOT_LONGER_THAN_PHRASE
  }