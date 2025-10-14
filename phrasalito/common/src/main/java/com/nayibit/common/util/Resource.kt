package com.nayibit.common.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOf

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<T>(val data: T): Resource<T>()
    data class Error(val message: String): Resource<Nothing>()
}

inline fun <A, B> Flow<Resource<A>>.flatMapSuccess(
    crossinline transform: (A) -> Flow<Resource<B>>
): Flow<Resource<B>> {
    return flatMapConcat { resource ->
        when (resource) {
            is Resource.Success -> transform(resource.data)
            is Resource.Error -> flowOf(Resource.Error(resource.message))
            Resource.Loading -> flowOf(Resource.Loading)
        }
    }
}
