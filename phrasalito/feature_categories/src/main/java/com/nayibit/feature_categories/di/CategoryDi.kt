package com.nayibit.feature_categories.di

import com.nayibit.feature_categories.data.repositories.CategoryRepositoryImpl
import com.nayibit.feature_categories.domain.repositories.CategoryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CategoryRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCategoryRepository(impl: CategoryRepositoryImpl): CategoryRepository
}
