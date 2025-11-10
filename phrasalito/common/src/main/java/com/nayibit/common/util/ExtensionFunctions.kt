package com.nayibit.common.util

fun String.normalizeSpaces(): String {
    return this.trim()               // remove leading/trailing spaces
        .replace("\\s+".toRegex(), " ") // replace multiple spaces/tabs/newlines with a single space
}


fun String.allowOnlyLettersAndSigns(): String {
    return this.replace("[^\\p{L}\\s,.'’\\-]".toRegex(), "")
}

fun String.removeLonelySigns(): String {
    return this
        // Remove punctuation/symbols that are directly before or after a space
        .replace("([\\p{P}\\p{S}])(?=\\s)".toRegex(), "")
        .replace("(?<=\\s)([\\p{P}\\p{S}])".toRegex(), "")
        // Remove punctuation at start or end
        .replace("^[\\p{P}\\p{S}]+|[\\p{P}\\p{S}]+$".toRegex(), "")
        // Normalize spaces
        .replace("\\s+".toRegex(), " ")
        .trim()
}

fun String.cleanRepeatedSigns(): String {
    // Replace 2 or more repeated non-letter/non-digit signs with a single one
    return this.replace(Regex("([^\\p{L}\\p{N}])\\1+"), "$1")
}


fun String.countValidChar(): Int {
    return count { it.isLetterOrDigit() }
}

 fun Char.isPunctuation(): Boolean = this in listOf(',', '.', '!', '?', ';', ':', '"', '\'', '(', ')')
