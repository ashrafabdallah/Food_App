package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import com.example.foodapp.domain.repository.FoodRepository
import kotlinx.coroutines.flow.Flow

class GetAllSavedMealsUseCase(private val repository: FoodRepository) {
     fun executeGetAllSavedMeals(): Flow<List<MealDB>>{
         return repository.getAllFavorite()
     }

}