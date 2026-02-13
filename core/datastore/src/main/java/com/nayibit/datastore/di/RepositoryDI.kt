package com.nayibit.datastore.di

import com.nayibit.datastore.data.GenericDataStore
import com.nayibit.datastore.data.repositories.DataStoreRepositoryImpl
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryDI {

    @Provides
    @Singleton
    fun provideDataStoreRepository(genericDataStore: GenericDataStore): DataStoreRepository {
        return DataStoreRepositoryImpl(genericDataStore)
    }
}