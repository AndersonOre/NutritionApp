package com.anderson.nutritionapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.anderson.nutritionapp.R

data class Page(
    val title:String,
    val description:String,
    @DrawableRes val image:Int
)

val pages = listOf<Page>(

    Page(

        "Discover Healthy Recipes",

        "Explore thousands of nutritious recipes tailored to your dietary goals. Whether you're vegan, keto, or just eating clean, find meals that fit your lifestyle.",

        R.drawable.onboarding1

    ),

    Page(

        "Track Calories with Precision",

        "Scan barcodes or search foods to get accurate calorie counts. Stay on top of your daily intake and make informed choices effortlessly.",

        R.drawable.onboarding2

    ),

    Page(

        "Know What’s in Your Food",

        "Get detailed nutritional breakdowns: macronutrients, vitamins, minerals, and more. Understand exactly what you're eating and why it matters.",

        R.drawable.onboarding3

    ),

    )