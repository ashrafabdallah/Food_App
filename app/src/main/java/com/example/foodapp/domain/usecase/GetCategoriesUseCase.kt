package com.example.foodapp.domain.usecase

import com.example.foodapp.data.model.CategoriesResponse
import com.example.foodapp.domain.repository.FoodRepository
import com.example.foodapp.util.Resource
import retrofit2.Response

class GetCategoriesUseCase(private val repository: FoodRepository) {
suspend fun executeGetCategoriesFromApi(): Resource<CategoriesResponse>{
    return repository.getCategories()
}
}