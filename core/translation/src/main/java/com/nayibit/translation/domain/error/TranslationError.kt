package com.nayibit.translation.domain.error

import com.nayibit.utils.helpers.Error

sealed interface TranslationError : Error {
    val message: String
    data class General(override val message: String) : TranslationError
}
