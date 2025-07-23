package com.anderson.nutritionapp.data.remote


import com.anderson.nutritionapp.data.remote.dto.FoodCategoriesResponseModel
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response

interface NutritionApi {

    @GET("food-categories/v2")
    suspend fun getFoodCategories(
        @Query("format") format: String = "json"
    ): Response<FoodCategoriesResponseModel>


}