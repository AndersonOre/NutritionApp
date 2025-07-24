package com.anderson.nutritionapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.anderson.nutritionapp.presentation.food_details.FoodDetailsScreen
import com.anderson.nutritionapp.presentation.food_search.FoodSearchScreen
import com.anderson.nutritionapp.presentation.home.HomeScreen
import com.anderson.nutritionapp.presentation.home.HomeViewModel
import com.anderson.nutritionapp.presentation.nutrition_navigator.NutritionNavigator
import com.anderson.nutritionapp.presentation.onboarding.OnBoardingScreen
import com.anderson.nutritionapp.presentation.onboarding.OnBoardingViewModel
import com.anderson.nutritionapp.presentation.recipe_search.RecipeSearchScreen
import com.anderson.nutritionapp.presentation.recipe_search.RecipeSearchViewModel

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route, startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = viewModel::onEvent
                )
            }
        }

        navigation(
            route = Route.NutritionNavigation.route,
            startDestination = Route.NutritionNavigationScreen.route
        ) {
            composable(route = Route.NutritionNavigationScreen.route){
                NutritionNavigator()
            }
        }


    }
}