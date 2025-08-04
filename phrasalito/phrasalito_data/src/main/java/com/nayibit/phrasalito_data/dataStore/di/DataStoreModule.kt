package com.nayibit.phrasalito_data.dataStore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.nayibit.phrasalito_data.dataStore.GenericDataStore
import com.nayibit.phrasalito_data.dataStore.dataStore
import com.nayibit.phrasalito_data.repository.DataStoreRepositoryImpl
import com.nayibit.phrasalito_domain.repository.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }


    @Provides
    @Singleton
    fun provideGenericDataStore(@ApplicationContext context: Context): GenericDataStore {
        return GenericDataStore(context)
    }

    @Provides
    @Singleton
    fun provideDataStoreRepository(genericDataStore: GenericDataStore): DataStoreRepository {
        return DataStoreRepositoryImpl(genericDataStore)
    }

}



