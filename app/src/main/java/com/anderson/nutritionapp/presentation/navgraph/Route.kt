package com.anderson.nutritionapp.presentation.navgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen : Route(route = "onboarding_screen")
    object HomeScreen : Route(route = "home_screen")
    object SearchScreen : Route(route = "search_screen")
    //...
    object AppStartNavigation : Route(route = "app_start_navigation")
    object NutritionNavigation : Route(route = "nutrition_navigation")
    object NutritionNavigationScreen : Route(route = "nutrition_navigation_screen")
}