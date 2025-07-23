package com.anderson.nutritionapp.domain.repository

import com.anderson.nutritionapp.data.remote.dto.FoodByIdResponseModel
import com.anderson.nutritionapp.data.remote.dto.FoodSearchResponseModel
import com.anderson.nutritionapp.domain.model.FoodCategoryModel
import kotlinx.coroutines.flow.Flow

interface NutritionRepository {

    fun getFoodCategories(): Flow<List<FoodCategoryModel>>

    fun searchFoods(
        searchExpression: String,
        pageNumber: Int? = null,
        maxResults: Int? = null
    ): Flow<FoodSearchResponseModel>

    suspend fun getFoodById(foodId: String): FoodByIdResponseModel?

}