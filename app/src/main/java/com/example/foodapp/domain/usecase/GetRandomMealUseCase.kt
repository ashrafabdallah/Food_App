package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.RandomResponse
import com.example.foodapp.domain.repository.FoodRepository
import com.example.foodapp.util.Resource
import retrofit2.Response

class GetRandomMealUseCase(private val repository: FoodRepository) {
    suspend fun executeGetRandomMeal(): Resource<RandomResponse>{
        return repository.getRandomMeal()
    }
}