package com.anderson.nutritionapp.data.remote.dto

data class RecipeDetailsResponseModel(
    val recipe: RecipeDetail
)

data class RecipeDetail(
    val recipe_id: String,
    val recipe_name: String,
    val recipe_description: String,
    val recipe_url: String,
    val recipe_images: RecipeImages?,
    val recipe_types: RecipeTypes?,
    val recipe_categories: RecipeCategories?,
    val ingredients: Ingredients?,
    val directions: Directions?,
    val number_of_servings: String?,
    val preparation_time_min: String?,
    val cooking_time_min: String?,
    val grams_per_portion: String?,
    val rating: String?,
    val serving_sizes: ServingSizes?
)

data class RecipeImages(val recipe_image: List<String>?)
data class RecipeCategories(val recipe_category: List<RecipeCategory>?)
data class RecipeCategory(val recipe_category_name: String, val recipe_category_url: String)
data class Ingredients(val ingredient: List<Ingredient>?)
data class Ingredient(
    val food_id: String,
    val food_name: String,
    val ingredient_description: String,
    val ingredient_url: String,
    val measurement_description: String,
    val number_of_units: String,
    val serving_id: String
)
data class Directions(val direction: List<Direction>?)
data class Direction(
    val direction_description: String,
    val direction_number: String
)
data class ServingSizes(val serving: Serving?)
