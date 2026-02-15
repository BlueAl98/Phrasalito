package com.nayibit.datastore.utils

import com.nayibit.datastore.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow

inline fun <reified T : Any> DataStoreRepository.getData(
    key: String,
    defaultValue: T
): Flow<T> {
    return getData(key, defaultValue, T::class)
}