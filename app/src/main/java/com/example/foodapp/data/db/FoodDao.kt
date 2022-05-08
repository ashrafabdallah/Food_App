package com.example.foodapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(mealDetail: MealDB)
    @Update
    suspend fun  updateFavorite(mealDetail: MealDB)
    @Query("SELECT * FROM meal_information order by mealId asc")
    fun getAllSavedMeals(): Flow<List<MealDB>>

    @Query("SELECT * FROM meal_information WHERE mealId =:id")
    fun getMealById(id:String):Flow<MealDB>

    @Query("DELETE FROM meal_information WHERE mealId =:id")
   suspend fun deleteMealById(id:String)

    @Delete
    suspend fun deleteMeal(meal:MealDB)

}