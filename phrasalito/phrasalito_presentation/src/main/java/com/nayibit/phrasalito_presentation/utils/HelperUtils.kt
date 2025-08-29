package com.nayibit.phrasalito_presentation.utils

import com.nayibit.common.util.normalizeSpaces


fun validateExample(phrase: String, example: String = ""): ValidateExampleResult {
        val normalizedPhrase = phrase.trim().lowercase().normalizeSpaces()
        val normalizedExample = example.trim().lowercase().normalizeSpaces()

        if (example.isEmpty()) return ValidateExampleResult.IS_VALID
        // Example must contain phrase
        if (!normalizedExample.contains(normalizedPhrase)) return ValidateExampleResult.EXAMPLE_NOT_CONTAINS_PHRASE

        // Example must be longer than phrase + at least some extra context
        if (normalizedExample.length <= normalizedPhrase.length + 3) return ValidateExampleResult.EXAMPLE_IS_NOT_LONGER_THAN_PHRASE

        return ValidateExampleResult.IS_VALID
    }

  enum class ValidateExampleResult {
      IS_VALID,
      EXAMPLE_NOT_CONTAINS_PHRASE,
      EXAMPLE_IS_NOT_LONGER_THAN_PHRASE
  }