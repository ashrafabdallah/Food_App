package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealsResponse
import com.example.foodapp.domain.repository.FoodRepository
import com.example.foodapp.util.Resource
import retrofit2.Response

class GetMealsByCategoryUseCase(private val repository: FoodRepository) {
    suspend fun executeGetMealsByCategory(categoryId: String): Resource<MealsResponse>{
        return repository.getMealsByCategory(categoryId)
    }
}