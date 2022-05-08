package com.example.foodapp.data.repository.datasourceimpl

import com.example.foodapp.data.api.FoodApi
import com.example.foodapp.data.model.CategoriesResponse
import com.example.foodapp.data.model.MealsResponse
import com.example.foodapp.data.model.RandomResponse
import com.example.foodapp.data.repository.datasource.RemoteDataSource
import retrofit2.Response

class RemoteDataSourceIMPL(private val foodApi: FoodApi): RemoteDataSource {
    override suspend fun getCategories(): Response<CategoriesResponse> {
        return foodApi.getCategories()
    }

    override suspend fun getMealsByCategory(categoryId: String): Response<MealsResponse> {
       return foodApi.getMealsByCategory(categoryId)
    }

    override suspend fun getRandomMeal(): Response<RandomResponse> {
       return foodApi.getRandomMeal()
    }

    override suspend fun getMealById(id: String): Response<RandomResponse> {
        return foodApi.getMealById(id)
    }

    override suspend fun getMealByName(s: String): Response<RandomResponse> {
        return foodApi.getMealByName(s)
    }
}