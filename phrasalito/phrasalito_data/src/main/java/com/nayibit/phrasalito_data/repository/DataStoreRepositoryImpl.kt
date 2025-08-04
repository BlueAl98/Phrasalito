package com.nayibit.phrasalito_data.repository

import com.nayibit.phrasalito_data.dataStore.GenericDataStore
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: GenericDataStore
): DataStoreRepository {
    override suspend fun <T> saveData(key: String, value: T) {
        dataStore.saveData(key, value)
    }

    override fun <T> getData(
        key: String,
        defaultValue: T
    ): Flow<T?> {
        return dataStore.getData(key, defaultValue)
    }

    override suspend fun clearData(key: String) {
        dataStore.clearData(key)
    }

    override suspend fun saveMultipleData(data: Map<String, Any>) {
        dataStore.saveMultipleData(data)
    }
}