package com.example.foodapp.data.repository.datasource

import com.example.foodapp.data.model.CategoriesResponse
import com.example.foodapp.data.model.MealsResponse
import com.example.foodapp.data.model.RandomResponse
import com.example.foodapp.util.Resource
import retrofit2.Response

interface RemoteDataSource {

    suspend fun getCategories(): Response<CategoriesResponse>
    suspend fun getMealsByCategory(categoryId: String): Response<MealsResponse>
    suspend fun getRandomMeal(): Response<RandomResponse>
    suspend fun getMealById(id: String):Response<RandomResponse>
    suspend fun getMealByName(s: String): Response<RandomResponse>
}