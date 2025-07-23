package com.anderson.nutritionapp.domain.usecase.nutrition

import com.anderson.nutritionapp.domain.model.FoodCategoryModel
import com.anderson.nutritionapp.domain.repository.NutritionRepository
import kotlinx.coroutines.flow.Flow

class GetFoodCategories(
    private val nutritionRepository: NutritionRepository
) {

    operator fun invoke(): Flow<List<FoodCategoryModel>> {
        return nutritionRepository.getFoodCategories()
    }

}