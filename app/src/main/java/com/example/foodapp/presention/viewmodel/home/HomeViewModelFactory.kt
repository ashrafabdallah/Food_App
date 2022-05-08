package com.example.foodapp.presention.viewmodel.home

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.foodapp.domain.usecase.*

class HomeViewModelFactory(
    private val app: Application,
    private val getRandomMealUseCase: GetRandomMealUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getMealByNameUseCase: GetMealByNameUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getAllSavedMealsUseCase: GetAllSavedMealsUseCase,
    private val gGetMealByIdSavedUseCase: GetMealByIdSavedUseCase,
    private val deleteMealUseCase: DeleteMealUseCase,
    private val deleteMealByIdUseCase: DeleteMealByIdUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(
                app,
                getRandomMealUseCase,
                getCategoriesUseCase,
                getMealsByCategoryUseCase,
                getMealByIdUseCase,
                getMealByNameUseCase,
                insertFavoriteUseCase, getAllSavedMealsUseCase,
                gGetMealByIdSavedUseCase,
                deleteMealUseCase,
                deleteMealByIdUseCase,
                updateFavoriteUseCase
            ) as T
        }
        throw IllegalAccessException("Unknown View Model Class ......")
    }
}