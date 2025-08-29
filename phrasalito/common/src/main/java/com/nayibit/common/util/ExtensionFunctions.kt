package com.nayibit.common.util

fun String.normalizeSpaces(): String {
    return this.trim()               // remove leading/trailing spaces
        .replace("\\s+".toRegex(), " ") // replace multiple spaces/tabs/newlines with a single space
}