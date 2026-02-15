package com.nayibit.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.reflect.KClass
import kotlin.text.toBoolean
import kotlin.text.toFloat
import kotlin.text.toInt
import kotlin.text.toLong

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "global_datastore")

class GenericDataStore @Inject constructor(
     val context: Context
) {

    suspend fun <T> saveData(key: String, value: T) {
        val preferencesKey = stringPreferencesKey(key)
        context.dataStore.edit { preferences ->
            preferences[preferencesKey] = value.toString()
        }
    }


    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getData(
        key: String,
        defaultValue: T,
        clazz: KClass<T>
    ): Flow<T> {

        val preferencesKey = stringPreferencesKey(key)

        return context.dataStore.data.map { preferences ->

            val rawValue = preferences[preferencesKey]
                ?: return@map defaultValue

            val converted = when (clazz) {
                String::class -> rawValue
                Int::class -> rawValue.toIntOrNull()
                Boolean::class -> rawValue.toBooleanStrictOrNull()
                Float::class -> rawValue.toFloatOrNull()
                Long::class -> rawValue.toLongOrNull()
                else -> throw IllegalArgumentException("Unsupported type")
            }

            (converted as? T) ?: defaultValue
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