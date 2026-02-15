package com.nayibit.datastore.data.repositories

import com.nayibit.datastore.data.GenericDataStore
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.KClass

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: GenericDataStore
) : DataStoreRepository {

    override suspend fun <T> saveData(key: String, value: T) {
        dataStore.saveData(key, value)
    }

    override fun <T: Any> getData(
        key: String,
        defaultValue: T,
        clazz: KClass<T>
    ): Flow<T> {
        return  dataStore.getData(key, defaultValue, clazz)
    }

    override suspend fun clearData(key: String) {
        dataStore.clearData(key)
    }

    override suspend fun saveMultipleData(data: Map<String, Any>) {
        dataStore.saveMultipleData(data)
    }


}

