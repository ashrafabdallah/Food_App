package com.example.foodapp.presention.viewmodel.splash

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SplashViewModelFactory(private val app: Application):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SplashViewModel::class.java)){
            return SplashViewModel(app) as T
        }
        throw IllegalAccessException("Unknown View Model Class ......")
    }

}