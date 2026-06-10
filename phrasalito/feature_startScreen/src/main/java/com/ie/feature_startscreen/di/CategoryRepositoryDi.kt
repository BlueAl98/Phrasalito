package com.ie.feature_startscreen.di

import com.ie.feature_startscreen.data.repositories.CategoryRepositoryImpl
import com.ie.feature_startscreen.domain.repositories.CategoryRepository
import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.datastore.domain.repositories.DataStoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CategoryRepositoryDi {

    @Provides
    fun provideDeckRepository(dao: CategoryDao, dataStore: DataStoreRepository) : CategoryRepository {
        return CategoryRepositoryImpl(dao, dataStore)
    }

}