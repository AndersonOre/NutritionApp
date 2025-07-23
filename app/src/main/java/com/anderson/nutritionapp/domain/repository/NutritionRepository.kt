package com.anderson.nutritionapp.domain.repository

import com.anderson.nutritionapp.domain.model.FoodCategoryModel
import kotlinx.coroutines.flow.Flow

interface NutritionRepository {

    fun getFoodCategories(): Flow<List<FoodCategoryModel>>

}