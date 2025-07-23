package com.anderson.nutritionapp.domain.usecase.nutrition

import com.anderson.nutritionapp.domain.repository.NutritionRepository

class SearchFoods(
    private val repository: NutritionRepository
) {
    operator fun invoke(
        searchExpression: String,
        pageNumber: Int? = null,
        maxResults: Int? = null
    ) = repository.searchFoods(searchExpression, pageNumber, maxResults)
}