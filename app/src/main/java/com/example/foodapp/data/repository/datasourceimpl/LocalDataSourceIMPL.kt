package com.example.foodapp.data.repository.datasourceimpl

import com.example.foodapp.data.db.FoodDao
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.data.repository.datasource.LocalDataSource
import kotlinx.coroutines.flow.Flow

class LocalDataSourceIMPL(private val foodDao: FoodDao) : LocalDataSource {
    override suspend fun saveFavorite(mealDetail: MealDB) {
        foodDao.insertFavorite(mealDetail)
    }

    override suspend fun updateFavorite(mealDetail: MealDB) {
        foodDao.updateFavorite(mealDetail)
    }

    override fun getAllFavorite(): Flow<List<MealDB>> {
       return foodDao.getAllSavedMeals()
    }

    override suspend fun getMealSavedById(id: String): Flow<MealDB> {
       return foodDao.getMealById(id)
    }

    override suspend fun deleteMealById(id: String) {
        foodDao.deleteMealById(id)
    }

    override suspend fun deleteMeal(meal: MealDB) {
        foodDao.deleteMeal(meal)
    }
}