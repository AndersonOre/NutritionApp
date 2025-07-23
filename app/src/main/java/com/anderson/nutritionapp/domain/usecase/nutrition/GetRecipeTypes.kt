package com.anderson.nutritionapp.domain.usecase.nutrition

import com.anderson.nutritionapp.domain.repository.NutritionRepository

class GetRecipeTypes(private val repository: NutritionRepository) {
    operator fun invoke() = repository.getRecipeTypes()
}