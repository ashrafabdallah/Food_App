package com.example.foodapp.presention.di

import android.app.Application
import com.example.foodapp.presention.viewmodel.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class SplashViewModelFactoryModule {

    @Provides
    @Singleton
    fun provideSplashViweModel(app: Application): SplashViewModelFactory {
        return SplashViewModelFactory(app)
    }
}