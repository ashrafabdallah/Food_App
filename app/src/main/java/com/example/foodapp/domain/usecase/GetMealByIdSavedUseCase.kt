package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetMealByIdSavedUseCase(private val repository: FoodRepository)
{
    suspend fun executeGetMealByIdSaved(id:String):Flow<MealDB>  {
        return repository.getMealSavedById(id)
    }
}