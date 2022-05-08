package com.example.foodapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "meal_information")
data class MealDB(
    @PrimaryKey
    var mealId: Int?=null,
    var mealName: String?,
    var mealCountry: String?,
    var mealCategory:String?,
    var mealInstruction:String?,
    var mealThumb:String?,
    var mealYoutubeLink:String?
):Serializable
