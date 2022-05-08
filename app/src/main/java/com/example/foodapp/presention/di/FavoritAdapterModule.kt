package com.example.foodapp.presention.di

import com.example.foodapp.presention.adapters.CategoryItemAdapter
import com.example.foodapp.presention.adapters.FavoritAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FavoritAdapterModule {
    @Singleton
    @Provides
    fun provideFavoritAdapter(): FavoritAdapter {
        return FavoritAdapter()
    }
}