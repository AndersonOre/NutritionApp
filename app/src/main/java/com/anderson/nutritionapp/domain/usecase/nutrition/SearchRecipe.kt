package com.anderson.nutritionapp.domain.usecase.nutrition

import com.anderson.nutritionapp.domain.repository.NutritionRepository

class SearchRecipes(private val repository: NutritionRepository) {
    operator fun invoke(recipeType: String, maxResults: Int = 30) = repository.searchRecipes(recipeType, maxResults)
}