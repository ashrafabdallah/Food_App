package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.RandomResponse
import com.example.foodapp.domain.repository.FoodRepository
import com.example.foodapp.util.Resource
import retrofit2.Response

class GetMealByNameUseCase(private val repository: FoodRepository)
{
    suspend fun executeGetMealByName(s: String): Resource<RandomResponse>{
        return repository.getMealByName(s)
    }
}
