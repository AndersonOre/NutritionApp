package com.anderson.nutritionapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.anderson.nutritionapp.data.remote.dto.Food

@Database(entities = [Food::class], version = 1)
@TypeConverters(FoodTypeConverters::class)
abstract class NutritionDatabase : RoomDatabase() {
    abstract val foodDao: FoodDao
}