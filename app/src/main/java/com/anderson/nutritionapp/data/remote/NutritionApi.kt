package com.anderson.nutritionapp.data.remote


import com.anderson.nutritionapp.data.remote.dto.FoodByIdResponseModel
import com.anderson.nutritionapp.data.remote.dto.FoodCategoriesResponseModel
import com.anderson.nutritionapp.data.remote.dto.FoodSearchResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeSearchResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeTypesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface NutritionApi {

    @GET("food-categories/v2")
    suspend fun getFoodCategories(
        @Query("format") format: String = "json"
    ): Response<FoodCategoriesResponseModel>


    @GET("foods/search/v3")
    suspend fun searchFoods(
        @Query("search_expression") searchExpression: String,
        @Query("page_number") pageNumber: Int? = null,
        @Query("max_results") maxResults: Int? = null,
        @Query("format") format: String = "json",
        @Query("include_food_images") includeFoodImages: Boolean = true
    ): Response<FoodSearchResponseModel>


    @GET("food/v4")
    suspend fun getFoodById(
        @Query("food_id") foodId: String,
        @Query("format") format: String = "json",
        @Query("include_food_images") includeFoodImages: Boolean = true
    ): Response<FoodByIdResponseModel>

    @GET("recipe-types/v2")
    suspend fun getRecipeTypes(
        @Query("format") format: String = "json"
    ): Response<RecipeTypesResponseModel>

    @GET("recipes/search/v3")
    suspend fun searchRecipes(
        @Query("recipe_types") recipeType: String,
        @Query("max_results") maxResults: Int = 20,
        @Query("format") format: String = "json"
    ): Response<RecipeSearchResponseModel>

}