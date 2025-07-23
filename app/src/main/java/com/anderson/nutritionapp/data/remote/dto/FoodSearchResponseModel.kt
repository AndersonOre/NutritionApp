package com.anderson.nutritionapp.data.remote.dto


data class FoodSearchResponseModel(
    val foods_search: FoodsSearch
)

data class FoodsSearch(
    val max_results: String,
    val total_results: String,
    val page_number: String,
    val results: FoodResults
)

data class FoodResults(
    val food: List<Food>
)

data class Food(
    val food_id: String,
    val food_name: String,
    val food_type: String,
    val food_sub_categories: FoodSubCategories?,
    val food_url: String,
    val food_images: FoodImages?,
    val food_attributes: FoodAttributes?,
    val servings: Servings?
)

data class FoodSubCategories(
    val food_sub_category: List<String>
)

data class FoodImages(
    val food_image: List<FoodImage>
)

data class FoodImage(
    val image_url: String,
    val image_type: String
)

data class FoodAttributes(
    val allergens: Allergens?,
    val preferences: Preferences?
)

data class Allergens(
    val allergen: List<Allergen>
)

data class Allergen(
    val id: String,
    val name: String,
    val value: String
)

data class Preferences(
    val preference: List<Preference>
)

data class Preference(
    val id: String,
    val name: String,
    val value: String
)

data class Servings(
    val serving: List<Serving>
)

data class Serving(
    val serving_id: String,
    val serving_description: String,
    val serving_url: String,
    val metric_serving_amount: String,
    val metric_serving_unit: String,
    val number_of_units: String,
    val measurement_description: String,
    val calories: String,
    val carbohydrate: String,
    val protein: String,
    val fat: String,
    val saturated_fat: String,
    val polyunsaturated_fat: String,
    val monounsaturated_fat: String,
    val cholesterol: String,
    val sodium: String,
    val potassium: String,
    val fiber: String,
    val sugar: String,
    val vitamin_a: String,
    val vitamin_c: String,
    val calcium: String,
    val iron: String
)