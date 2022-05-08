package com.example.foodapp.data.repository.datasource

import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    suspend fun saveFavorite(mealDetail: MealDB)
    suspend fun updateFavorite(mealDetail: MealDB)
    fun getAllFavorite(): Flow<List<MealDB>>
    suspend fun getMealSavedById(id:String): Flow<MealDB>
    suspend fun deleteMealById(id:String)
    suspend fun deleteMeal(meal: MealDB)
}