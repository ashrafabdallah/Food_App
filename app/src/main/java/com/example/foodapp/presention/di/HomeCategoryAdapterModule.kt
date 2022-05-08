package com.example.foodapp.presention.di

import com.example.foodapp.presention.adapters.HomeCategoryAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeCategoryAdapterModule {

    @Singleton
    @Provides
    fun provideHomeCategoryAdapter(): HomeCategoryAdapter {
        return HomeCategoryAdapter()
    }
}