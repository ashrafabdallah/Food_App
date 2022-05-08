package com.example.foodapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.foodapp.data.model.Meal
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealDetail

@Database(entities = [MealDB::class,Meal::class], version = 1, exportSchema = false)
abstract class FoodRoomDataBase :RoomDatabase(){
    abstract fun getFoodDao():FoodDao
}