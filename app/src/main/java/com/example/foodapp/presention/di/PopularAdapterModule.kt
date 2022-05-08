package com.example.foodapp.presention.di

import com.example.foodapp.presention.adapters.PopularAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PopularAdapterModule {
    @Singleton
    @Provides
    fun providePopularAdapter(): PopularAdapter {
        return PopularAdapter()
    }
}