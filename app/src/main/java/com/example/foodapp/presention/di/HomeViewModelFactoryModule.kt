package com.example.foodapp.presention.di

import android.app.Application
import com.example.foodapp.domain.usecase.*
import com.example.foodapp.presention.viewmodel.home.HomeViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HomeViewModelFactoryModule {

    @Singleton
    @Provides
    fun providesHomeViewModelFactory(
        app: Application,
        getRandomMealUseCase: GetRandomMealUseCase,
        getCategoriesUseCase: GetCategoriesUseCase,
        getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
        getMealByIdUseCase: GetMealByIdUseCase, getMealByNameUseCase: GetMealByNameUseCase,
        insertFavoriteUseCase: InsertFavoriteUseCase,
        getAllSavedMealsUseCase: GetAllSavedMealsUseCase,
        getMealByIdSavedUseCase: GetMealByIdSavedUseCase, deleteMealUseCase: DeleteMealUseCase,
         deleteMealByIdUseCase: DeleteMealByIdUseCase,
         updateFavoriteUseCase: UpdateFavoriteUseCase
    ): HomeViewModelFactory {
        return HomeViewModelFactory(
            app,
            getRandomMealUseCase,
            getCategoriesUseCase,
            getMealsByCategoryUseCase,
            getMealByIdUseCase,
            getMealByNameUseCase,
            insertFavoriteUseCase,
            getAllSavedMealsUseCase,
            getMealByIdSavedUseCase,
            deleteMealUseCase,
            deleteMealByIdUseCase,
            updateFavoriteUseCase
        )
    }
}