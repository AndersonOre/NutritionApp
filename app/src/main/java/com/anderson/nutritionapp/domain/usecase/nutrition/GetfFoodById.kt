package com.anderson.nutritionapp.domain.usecase.nutrition

import com.anderson.nutritionapp.data.remote.dto.FoodByIdResponseModel
import com.anderson.nutritionapp.domain.repository.NutritionRepository

class GetFoodById(private val repository: NutritionRepository) {
    suspend operator fun invoke(foodId: String): FoodByIdResponseModel? {
        return repository.getFoodById(foodId)
    }
}