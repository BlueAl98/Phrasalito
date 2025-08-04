package com.nayibit.phrasalito_domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun <T> saveData(key: String, value: T)
    fun <T> getData(key: String, defaultValue: T): Flow<T?>
    suspend fun clearData(key: String)
    suspend fun saveMultipleData(data: Map<String, Any>)
}