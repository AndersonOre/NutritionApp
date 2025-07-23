package com.anderson.nutritionapp.data.remote.dto

data class RecipeTypesResponseModel(
    val recipe_types: RecipeTypesList
)

data class RecipeTypesList(
    val recipe_type: List<String>
)