package com.anderson.nutritionapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anderson.nutritionapp.data.remote.dto.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(food: Food)

    @Delete
    suspend fun delete(food: Food)

    @Query("SELECT * FROM Food")
    fun getFoods(): Flow<List<Food>>

    // In FoodDao.kt
    @Query("SELECT EXISTS(SELECT 1 FROM Food WHERE food_id = :foodId)")
    suspend fun isFavorite(foodId: String): Boolean
}