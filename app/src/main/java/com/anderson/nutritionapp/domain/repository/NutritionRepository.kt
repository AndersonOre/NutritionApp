package com.anderson.nutritionapp.domain.repository

import com.anderson.nutritionapp.data.remote.dto.FoodByIdResponseModel
import com.anderson.nutritionapp.data.remote.dto.FoodSearchResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeDetailsResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeSearchResponseModel
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

    fun getRecipeTypes(): Flow<List<String>>

    fun searchRecipes(recipeType: String, maxResults: Int = 30, searchExpression: String): Flow<RecipeSearchResponseModel>

    suspend fun getRecipeDetails(recipeId: String): RecipeDetailsResponseModel?

}