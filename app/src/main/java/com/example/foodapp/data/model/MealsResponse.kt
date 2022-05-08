package com.example.foodapp.data.model


import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class MealsResponse(
    @SerializedName("meals")
    var meals: List<Meal>
): Serializable