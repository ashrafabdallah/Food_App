package com.example.foodapp.data.api

import com.example.foodapp.data.model.CategoriesResponse
import com.example.foodapp.data.model.MealsResponse
import com.example.foodapp.data.model.RandomResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodApi {

    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriesResponse>

    @GET("filter.php?")
    suspend fun getMealsByCategory(@Query("i") category: String): Response<MealsResponse>

    @GET("random.php")
    suspend fun getRandomMeal(): Response<RandomResponse>

    @GET("lookup.php?")
    suspend fun getMealById(@Query("i") id: String): Response<RandomResponse>

    @GET("search.php?")
    suspend fun getMealByName(@Query("s") s: String): Response<RandomResponse>


}