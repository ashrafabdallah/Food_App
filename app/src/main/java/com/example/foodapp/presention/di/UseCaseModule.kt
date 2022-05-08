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
class UseCaseModule {

    @Singleton
    @Provides
    fun providesGetRandomMealUseCase(repository: FoodRepository): GetRandomMealUseCase {
        return GetRandomMealUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetCategoriesUseCase(repository: FoodRepository): GetCategoriesUseCase {
        return GetCategoriesUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetMealsByCategoryUseCase(repository: FoodRepository): GetMealsByCategoryUseCase {
        return GetMealsByCategoryUseCase(repository)
    }
    @Singleton
    @Provides
    fun providesGetMealsByIDUseCase(repository: FoodRepository): GetMealByIdUseCase {
        return GetMealByIdUseCase(repository)
    }

    @Singleton
    @Provides
    fun providesGetMealsGetMealByNameUseCase(repository: FoodRepository): GetMealByNameUseCase {
        return GetMealByNameUseCase(repository)
    }
}