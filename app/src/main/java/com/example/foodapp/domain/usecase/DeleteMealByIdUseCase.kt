package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.domain.repository.FoodRepository

class DeleteMealByIdUseCase(private val repository: FoodRepository) {
    suspend fun executeDeleteMealById(id:String){
        repository.deleteMealById(id)
    }
}