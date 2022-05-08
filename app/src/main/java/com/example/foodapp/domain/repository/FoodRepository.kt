package com.example.foodapp.domain.repository

import com.example.foodapp.data.model.*
import com.example.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Query

interface FoodRepository {

    suspend fun getCategories(): Resource<CategoriesResponse>
    suspend fun getMealsByCategory(categoryId: String): Resource<MealsResponse>
    suspend fun getRandomMeal(): Resource<RandomResponse>
    suspend fun getMealById(id: String): Resource<RandomResponse>
    suspend fun getMealByName(s: String):Resource<RandomResponse>

    // local DataBase

    suspend fun saveFavorite(mealDetail: MealDB)
    suspend fun updateFavorite(mealDetail: MealDB)
    fun getAllFavorite(): Flow<List<MealDB>>
   suspend fun getMealSavedById(id:String):Flow<MealDB>
    suspend fun deleteMealById(id:String)
    suspend fun deleteMeal(meal:MealDB)

}