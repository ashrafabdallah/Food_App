package com.example.foodapp.presention.di

import com.example.foodapp.data.db.FoodDao
import com.example.foodapp.data.repository.datasource.LocalDataSource
import com.example.foodapp.data.repository.datasourceimpl.LocalDataSourceIMPL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(foodDao: FoodDao): LocalDataSource {
        return LocalDataSourceIMPL(foodDao)
    }
}