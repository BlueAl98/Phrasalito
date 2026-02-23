package com.nayibit.feature_categories.di

import com.nayibit.database.room.dao.CategoryDao
import com.nayibit.feature_categories.data.repositories.CategoryRepositoryImpl
import com.nayibit.feature_categories.domain.repositories.CategoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CategoryDi {

    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao): CategoryRepository {
        return CategoryRepositoryImpl(categoryDao)
    }

}