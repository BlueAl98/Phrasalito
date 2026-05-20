package com.nayibit.feature_languages.domain.model

data class LanguageUi(
    val code: String,
    val displayName: String,
    val flagEmoji: String,
    val status: LanguageStatus
)

enum class LanguageStatus {
    AVAILABLE,
    DOWNLOADABLE,
    COMING_SOON
}
