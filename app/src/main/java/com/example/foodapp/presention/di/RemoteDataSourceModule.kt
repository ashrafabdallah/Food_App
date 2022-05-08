package com.example.foodapp.presention.di

import com.example.foodapp.data.api.FoodApi
import com.example.foodapp.data.repository.datasource.RemoteDataSource
import com.example.foodapp.data.repository.datasourceimpl.RemoteDataSourceIMPL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSourceModule {

    @Singleton
    @Provides
    fun providesRemoteResource(foodApi: FoodApi): RemoteDataSource {
        return RemoteDataSourceIMPL(foodApi)
    }
}