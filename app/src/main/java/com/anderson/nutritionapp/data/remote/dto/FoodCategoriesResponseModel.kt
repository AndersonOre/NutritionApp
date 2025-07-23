package com.anderson.nutritionapp.data.remote.dto


import com.anderson.nutritionapp.domain.model.FoodCategoriesModel
import com.google.gson.annotations.SerializedName


data class FoodCategoriesResponseModel(
    @SerializedName("food_categories")
    val foodCategories: FoodCategoriesModel?
)