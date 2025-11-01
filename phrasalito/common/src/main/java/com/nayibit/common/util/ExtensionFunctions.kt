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
        // Remove punctuation if it's followed by a space NOT followed by a letter, or end of text
        .replace("([,.'’\\-]+)(?=(\\s(?!\\p{L})|\$))".toRegex(), "")
        .replace("\\s+".toRegex(), " ") // normalize multiple spaces
        .trim()
}

fun String.cleanRepeatedSigns(): String {
    // Replace 2 or more repeated non-letter/non-digit signs with a single one
    return this.replace(Regex("([^\\p{L}\\p{N}])\\1+"), "$1")
}


fun String.countValidChar(): Int {
    return count { it.isLetterOrDigit() }
}