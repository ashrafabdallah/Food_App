package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.domain.repository.FoodRepository

class InsertFavoriteUseCase(private val repository: FoodRepository) {
    suspend fun executeInsertFavorite(mealDetail: MealDB){
        repository.saveFavorite(mealDetail)
    }
}