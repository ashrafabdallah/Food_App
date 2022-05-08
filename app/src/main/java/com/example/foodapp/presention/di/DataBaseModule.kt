package com.example.foodapp.presention.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodapp.data.db.FoodDao
import com.example.foodapp.data.db.FoodRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideDataBaseInstance(app:Application):FoodRoomDataBase{
        return Room.databaseBuilder(app,FoodRoomDataBase::class.java,"food_database3")
            // This will Allow Room to destructively replace database tables, if Migrations, that would migrate old database
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideArticalDAo(foodRoomDataBase:FoodRoomDataBase):FoodDao{
        return foodRoomDataBase.getFoodDao()
    }
}