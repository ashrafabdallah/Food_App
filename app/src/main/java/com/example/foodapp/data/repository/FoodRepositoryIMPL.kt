package com.example.foodapp.data.repository

import com.example.foodapp.data.model.*
import com.example.foodapp.data.repository.datasource.LocalDataSource
import com.example.foodapp.data.repository.datasource.RemoteDataSource
import com.example.foodapp.domain.repository.FoodRepository
import com.example.foodapp.util.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class FoodRepositoryIMPL(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource

) : FoodRepository {
    override suspend fun getCategories(): Resource<CategoriesResponse> {
        return responseToResultResourceCategoryResponse(remoteDataSource.getCategories())

    }

    override suspend fun getMealsByCategory(categoryId: String): Resource<MealsResponse> {
        return responseToResultResourceMEalResponse(remoteDataSource.getMealsByCategory(categoryId))
    }

    override suspend fun getRandomMeal(): Resource<RandomResponse> {
        return responseToResultResource(remoteDataSource.getRandomMeal())
    }

    override suspend fun getMealById(id: String): Resource<RandomResponse> {
        return responseToResultResource(remoteDataSource.getMealById(id))
    }

    override suspend fun getMealByName(s: String): Resource<RandomResponse> {
        return responseToResultResource(remoteDataSource.getMealByName(s))
    }


    private fun responseToResultResource(response: Response<RandomResponse>): Resource<RandomResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)

            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResultResourceMEalResponse(response: Response<MealsResponse>): Resource<MealsResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)

            }
        }
        return Resource.Error(response.message())
    }

    private fun responseToResultResourceCategoryResponse(response: Response<CategoriesResponse>): Resource<CategoriesResponse> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)

            }
        }
        return Resource.Error(response.message())
    }


    // Local DataBase Operation


    override suspend fun saveFavorite(mealDetail: MealDB) {
        localDataSource.saveFavorite(mealDetail)
    }

    override suspend fun updateFavorite(mealDetail: MealDB) {
        localDataSource.updateFavorite(mealDetail)
    }

    override fun getAllFavorite(): Flow<List<MealDB>> {
        return localDataSource.getAllFavorite()
    }

    override suspend fun getMealSavedById(id: String): Flow<MealDB> {
        return localDataSource.getMealSavedById(id)
    }

    override suspend fun deleteMealById(id: String) {
        localDataSource.deleteMealById(id)
    }

    override suspend fun deleteMeal(meal: MealDB) {
        localDataSource.deleteMeal(meal)
    }


}