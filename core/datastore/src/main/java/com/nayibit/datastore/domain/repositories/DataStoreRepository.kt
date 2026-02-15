package com.nayibit.datastore.domain.repositories

import kotlinx.coroutines.flow.Flow
import kotlin.reflect.KClass

interface DataStoreRepository {
    suspend fun <T> saveData(key: String, value: T)
    fun <T : Any> getData(
        key: String,
        defaultValue: T,
        clazz: KClass<T>
    ): Flow<T>
    suspend fun clearData(key: String)
    suspend fun saveMultipleData(data: Map<String, Any>)
}