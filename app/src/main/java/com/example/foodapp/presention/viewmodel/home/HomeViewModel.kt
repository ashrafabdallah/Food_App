package com.example.foodapp.presention.viewmodel.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.foodapp.data.model.CategoriesResponse
import com.example.foodapp.data.model.MealDB
import com.example.foodapp.data.model.MealsResponse
import com.example.foodapp.data.model.RandomResponse
import com.example.foodapp.domain.usecase.*
import com.example.foodapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Exception

class HomeViewModel(
    private val app: Application,
    private val getRandomMealUseCase: GetRandomMealUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMealsByCategoryUseCase: GetMealsByCategoryUseCase,
    private val getMealByIdUseCase: GetMealByIdUseCase,
    private val getMealByNameUseCase: GetMealByNameUseCase,
    private val insertFavoriteUseCase: InsertFavoriteUseCase,
    private val getAllSavedMealsUseCase: GetAllSavedMealsUseCase,
    private val gGetMealByIdSavedUseCase: GetMealByIdSavedUseCase,
    private val deleteMealUseCase: DeleteMealUseCase,
    private val deleteMealByIdUseCase: DeleteMealByIdUseCase,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase


) : AndroidViewModel(app) {

    var connectData: MutableLiveData<String> = MutableLiveData()
    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false

    }

    fun getConnect() {
        if (isNetworkAvailable(app)) {
            connectData.postValue("ok")
        } else {
            connectData.postValue("no")
        }
    }

    // RandomImage
    var imageMutable: MutableLiveData<Resource<RandomResponse>> = MutableLiveData()
    fun getRandomImage() = viewModelScope.launch(Dispatchers.IO) {
        imageMutable.postValue(Resource.Loading())
        try {
            if (connectData.value == "ok") {
                var response = getRandomMealUseCase.executeGetRandomMeal()
                imageMutable.postValue(response)
            } else {
                imageMutable.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            imageMutable.postValue(Resource.Error(e.message.toString()))
        }

    }

    // PopularMeal
    var popularMeal: MutableLiveData<Resource<MealsResponse>> = MutableLiveData()
    fun getPopularMeal(id: String) = viewModelScope.launch(Dispatchers.IO) {
        popularMeal.postValue(Resource.Loading())
        try {
            if (connectData.value == "ok") {
                var result = getMealsByCategoryUseCase.executeGetMealsByCategory(id)
                popularMeal.postValue(result)
            } else {
                popularMeal.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            popularMeal.postValue(Resource.Error(e.message.toString()))
        }
    }

    // Category
    var category: MutableLiveData<Resource<CategoriesResponse>> = MutableLiveData()
    fun getCategory() = viewModelScope.launch(Dispatchers.IO) {
        category.postValue(Resource.Loading())
        try {
            if (connectData.value == "ok") {
                var result = getCategoriesUseCase.executeGetCategoriesFromApi()
                category.postValue(result)
            } else {
                category.postValue(Resource.Error("Internet is not available"))
            }

        } catch (e: Exception) {
            category.postValue(Resource.Error(e.message.toString()))

        }
    }

    // getMealByID
    var mealByID: MutableLiveData<Resource<RandomResponse>> = MutableLiveData()
    fun getMealByID(id: String) = viewModelScope.launch(Dispatchers.IO) {
        mealByID.postValue(Resource.Loading())
        try {
            if (connectData.value == "ok") {
                var response = getMealByIdUseCase.executeGetMealByIdFromApi(id)
                mealByID.postValue(response)
            } else {
                mealByID.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            mealByID.postValue(Resource.Error(e.message.toString()))

        }
    }


    // get Meal by name
    var mealByName: MutableLiveData<Resource<RandomResponse>> = MutableLiveData()
    fun getMealByName(name: String) = viewModelScope.launch(Dispatchers.IO) {
        mealByID.postValue(Resource.Loading())
        try {
            if (connectData.value == "ok") {
                var response = getMealByNameUseCase.executeGetMealByName(name)
                mealByName.postValue(response)
            } else {
                mealByName.postValue(Resource.Error("Internet is not available"))
            }
        } catch (e: Exception) {
            mealByName.postValue(Resource.Error(e.message.toString()))

        }
    }


    // Local DataBase Operation
    fun saveFoodInFavorites(maels: MealDB) = viewModelScope.launch(Dispatchers.IO) {
        insertFavoriteUseCase.executeInsertFavorite(maels)
    }

    // get All Data From Local DataBase
    fun getFavoritsFromDataBase() = liveData {
        getAllSavedMealsUseCase.executeGetAllSavedMeals().collect {
            emit(it)
        }
    }

    // get Meal By ID
    fun getMealSavedByID(id: String?) = liveData {
        gGetMealByIdSavedUseCase.executeGetMealByIdSaved(id!!).collect {

            emit(it)
        }
    }

    // Delete Meal From DataBase
    fun deleteMealFromDataBase(maels: MealDB) = viewModelScope.launch(Dispatchers.IO) {
        deleteMealUseCase.executeDeleteMeals(maels)
    }

    fun deleteMealById(id: String) = viewModelScope.launch(Dispatchers.IO){
        deleteMealByIdUseCase.executeDeleteMealById(id)
    }
}