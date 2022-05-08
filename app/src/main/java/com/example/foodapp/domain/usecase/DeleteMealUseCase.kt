package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.domain.repository.FoodRepository

class DeleteMealUseCase(private val repository: FoodRepository) {
    suspend fun executeDeleteMeals(meal: MealDB){
        repository.deleteMeal(meal)
    }
}