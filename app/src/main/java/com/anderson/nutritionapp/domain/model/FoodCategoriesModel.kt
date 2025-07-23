package com.anderson.nutritionapp.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class FoodCategoriesModel(
    @SerializedName("food_category")
    val foodCategoryList: List<FoodCategoryModel>?
)