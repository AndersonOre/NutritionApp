package com.anderson.nutritionapp.data.repository

import com.anderson.nutritionapp.data.remote.NutritionApi
import com.anderson.nutritionapp.data.remote.dto.FoodByIdResponseModel
import com.anderson.nutritionapp.data.remote.dto.FoodSearchResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeDetailsResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeSearchResponseModel
import com.anderson.nutritionapp.domain.model.FoodCategoryModel
import com.anderson.nutritionapp.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NutritionRepositoryImpl(
    private val nutritionApi: NutritionApi) : NutritionRepository {

    override fun getFoodCategories(): Flow<List<FoodCategoryModel>> = flow {
        try {
            val response = nutritionApi.getFoodCategories()
            // Print the parsed response body
            android.util.Log.d("Repository", "Parsed body: ${response.body()}")
            // Print the raw error body if not successful
            if (!response.isSuccessful) {
                android.util.Log.e("Repository", "Error body: ${response.errorBody()?.string()}")
            }
            if (response.isSuccessful) {
                val categories = response.body()?.foodCategories?.foodCategoryList
                emit(categories ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            android.util.Log.e("Repository", "Exception: ", e)
            emit(emptyList())
        }
    }

    override fun searchFoods(
        searchExpression: String,
        pageNumber: Int?,
        maxResults: Int?
    ): Flow<FoodSearchResponseModel> = flow {
        try {
            val response = nutritionApi.searchFoods(searchExpression, pageNumber, maxResults)
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }
        } catch (e: Exception) {
            // Handle error as needed
        }
    }

    override suspend fun getFoodById(foodId: String): FoodByIdResponseModel? {
        return try {
            val response = nutritionApi.getFoodById(foodId)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    override fun getRecipeTypes(): Flow<List<String>> = flow {
        try {
            val response = nutritionApi.getRecipeTypes()
            if (response.isSuccessful) {
                val types = response.body()?.recipe_types?.recipe_type
                emit(types ?: emptyList())
            } else {
                emit(emptyList())
            }
        } catch (e: Exception) {
            emit(emptyList())
        }
    }

    override fun searchRecipes(recipeType: String, maxResults: Int, searchExpression: String): Flow<RecipeSearchResponseModel> = flow {
        try {
            val response = nutritionApi.searchRecipes(recipeType, maxResults,searchExpression)
            if (response.isSuccessful) {
                response.body()?.let { emit(it) }
            }
        } catch (e: Exception) {
            // Handle error as needed
        }
    }

    override suspend fun getRecipeDetails(recipeId: String): RecipeDetailsResponseModel? {
        return try {
            val response = nutritionApi.getRecipeDetails(recipeId)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

}