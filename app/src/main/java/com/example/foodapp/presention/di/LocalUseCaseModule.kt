package com.example.foodapp.presention.di

import com.example.foodapp.domain.repository.FoodRepository
import com.example.foodapp.domain.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class LocalUseCaseModule {




    @Singleton
    @Provides
    fun provideInsertFavoriteUseCase(repository: FoodRepository): InsertFavoriteUseCase {
        return InsertFavoriteUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetAllFavorites(repository: FoodRepository):GetAllSavedMealsUseCase{
        return GetAllSavedMealsUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideGetMealByIdSavedUseCase(repository: FoodRepository):GetMealByIdSavedUseCase{
        return GetMealByIdSavedUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteMealUseCase(repository: FoodRepository):DeleteMealUseCase{
        return DeleteMealUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideUpdateFavoriteUseCase(repository: FoodRepository):UpdateFavoriteUseCase{
        return UpdateFavoriteUseCase(repository)
    }
    @Singleton
    @Provides
    fun provideDeleteMealByIdUseCase(repository: FoodRepository):DeleteMealByIdUseCase{
        return DeleteMealByIdUseCase(repository)
    }
}