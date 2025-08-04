package com.nayibit.phrasalito_data.dataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.text.toBoolean
import kotlin.text.toFloat
import kotlin.text.toInt
import kotlin.text.toLong


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "global_datastore")

class GenericDataStore @Inject constructor(
    private val context: Context
)  {

    suspend fun <T> saveData(key: String, value: T) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value.toString()
        }
    }

    fun <T> getData(key: String, defaultValue: T): Flow<T> {
        val preferencesKey = stringPreferencesKey(key)
        return context.dataStore.data.map { preferences ->
            val value = preferences[preferencesKey] ?: return@map defaultValue
            when (defaultValue) {
                is String -> value as T
                is Int -> value.toInt() as T
                is Boolean -> value.toBoolean() as T
                is Float -> value.toFloat() as T
                is Long -> value.toLong() as T
                else -> throw IllegalArgumentException("Unsupported type")
            }
        }
    }



    suspend fun clearData(key: String) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences.remove(preferencesKey)
        }
    }

    suspend fun saveMultipleData(data: Map<String, Any>) {
        context.dataStore.edit { preferences ->
            data.forEach { (key, value) ->
                val preferencesKey = stringPreferencesKey(key)
                preferences[preferencesKey] = value.toString()
            }
        }
    }



}