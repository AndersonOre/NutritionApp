package com.anderson.nutritionapp.presentation.navgraph

import androidx.navigation.NamedNavArgument

sealed class Route(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList()
) {
    object OnBoardingScreen : Route(route = "onboarding_screen")
    object FoodSearchScreen : Route(route = "food_search_screen")
    object HomeScreen : Route(route = "home_screen")
    object SearchScreen : Route(route = "search_screen")
    object FoodDetailsScreen : Route(route = "food_details_screen")
    object FoodFavoritesScreen : Route(route = "food_favorites_screen")
    //...
    object AppStartNavigation : Route(route = "app_start_navigation")
    object NutritionNavigation : Route(route = "nutrition_navigation")
    object NutritionNavigationScreen : Route(route = "nutrition_navigation_screen")
}