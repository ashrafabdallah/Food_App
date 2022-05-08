package com.example.foodapp.presention.di

import com.example.foodapp.presention.adapters.CategoryItemAdapter
import com.example.foodapp.presention.adapters.SearchAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SearchAdapterModule {
    @Singleton
    @Provides
    fun provideCategoryItemAdapter(): SearchAdapter {
        return SearchAdapter()
    }
}