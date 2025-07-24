package com.anderson.nutritionapp.domain.usecase.nutrition

import com.anderson.nutritionapp.data.remote.dto.RecipeDetailsResponseModel
import com.anderson.nutritionapp.domain.repository.NutritionRepository

class GetRecipeDetails(private val repository: NutritionRepository) {
    suspend operator fun invoke(recipeId: String): RecipeDetailsResponseModel? {
        return repository.getRecipeDetails(recipeId)
    }
}