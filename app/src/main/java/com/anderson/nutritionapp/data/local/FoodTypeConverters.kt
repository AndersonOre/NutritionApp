package com.anderson.nutritionapp.data.local

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.anderson.nutritionapp.data.remote.dto.*
import com.google.gson.Gson

@ProvidedTypeConverter
class FoodTypeConverters {
    private val gson = Gson()

    @TypeConverter
    fun fromFoodSubCategories(value: FoodSubCategories?): String? = gson.toJson(value)
    @TypeConverter
    fun toFoodSubCategories(value: String?): FoodSubCategories? =
        value?.let { gson.fromJson(it, FoodSubCategories::class.java) }

    @TypeConverter
    fun fromFoodImages(value: FoodImages?): String? = gson.toJson(value)
    @TypeConverter
    fun toFoodImages(value: String?): FoodImages? =
        value?.let { gson.fromJson(it, FoodImages::class.java) }

    @TypeConverter
    fun fromFoodAttributes(value: FoodAttributes?): String? = gson.toJson(value)
    @TypeConverter
    fun toFoodAttributes(value: String?): FoodAttributes? =
        value?.let { gson.fromJson(it, FoodAttributes::class.java) }

    @TypeConverter
    fun fromServings(value: Servings?): String? = gson.toJson(value)
    @TypeConverter
    fun toServings(value: String?): Servings? =
        value?.let { gson.fromJson(it, Servings::class.java) }
}