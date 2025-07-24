package com.anderson.nutritionapp.domain.usecase.nutrition

data class NutritionUseCases(
    val getFoodCategories: GetFoodCategories,
    val getFoodById: GetFoodById,
    val searchFoods: SearchFoods,
    val getRecipeTypes: GetRecipeTypes,
    val searchRecipes: SearchRecipes,
    val getRecipeDetails: GetRecipeDetails
)