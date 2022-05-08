package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.domain.repository.FoodRepository

class UpdateFavoriteUseCase(private val repository: FoodRepository) {
    suspend fun executeUpdateFavorite(mealDetail: MealDB){
        repository.updateFavorite(mealDetail)
    }
}