package com.example.foodapp.presention.di

import com.example.foodapp.data.repository.FoodRepositoryIMPL
import com.example.foodapp.data.repository.datasource.LocalDataSource
import com.example.foodapp.data.repository.datasource.RemoteDataSource
import com.example.foodapp.domain.repository.FoodRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FoodRepositoryModule {

    @Singleton
    @Provides
    fun providesFoodRepository(
        remoteDataSource: RemoteDataSource,
        localDataSource: LocalDataSource

    ): FoodRepository {
        return FoodRepositoryIMPL(remoteDataSource,localDataSource)
    }
}