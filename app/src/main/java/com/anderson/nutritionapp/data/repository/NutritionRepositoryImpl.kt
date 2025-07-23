package com.anderson.nutritionapp.data.repository

import com.anderson.nutritionapp.data.remote.NutritionApi
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

}