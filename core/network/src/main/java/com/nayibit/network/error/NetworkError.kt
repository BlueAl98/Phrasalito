package com.nayibit.network.error

import com.nayibit.utils.helpers.Error

sealed class NetworkError : Error {
    data class Http(val status: Int, val message: String) : NetworkError()
    data object NoInternet : NetworkError()
    data object Timeout : NetworkError()
    data object Unknown : NetworkError()
}
