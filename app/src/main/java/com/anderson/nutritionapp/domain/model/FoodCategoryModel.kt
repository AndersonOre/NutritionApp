package com.anderson.nutritionapp.domain.model


import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

data class FoodCategoryModel(
    @SerializedName("food_category_id")
    val id: String,

    @SerializedName("food_category_name")
    val name: String,

    @SerializedName("food_category_description")
    val description: String
)