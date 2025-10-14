package com.nayibit.phrasalito_presentation.mappers

import com.nayibit.phrasalito_presentation.screens.startScreen.Language
import java.util.Locale

fun Locale.toLanguage(): Language {
    return Language(
        id = this.hashCode(),
        language = this.displayName,
        alias = this.language
    )
}
