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
            composable(route = Route.NutritionNavigationScreen.route) {

                val viewModel: HomeViewModel = hiltViewModel()
                val categories = viewModel.food_categories.collectAsState(initial = emptyList())
                val recipeTypes = viewModel.recipeTypes.collectAsState(initial = emptyList())

                HomeScreen(
                    categories = categories.value,
                    recipes = recipeTypes.value,
                    onCategoryClick = { category ->
                        navController.navigate("${Route.FoodSearchScreen}/${category.name}")
                    },
                    onRecipeClick = { recipeType ->
                        navController.navigate("RecipeTypeScreen/$recipeType")
                    }
                )

            }
            composable(route = "${Route.FoodSearchScreen}/{categoryName}") { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                androidx.compose.runtime.LaunchedEffect(categoryName) {
                    viewModel.searchFoods(categoryName)
                }
                val foodSearchResults = viewModel.foodSearchResults.collectAsState().value
                FoodSearchScreen(
                    categoryName = categoryName,
                    foodSearchResults = foodSearchResults,
                    onFoodClick = { foodId ->
                        navController.navigate("${Route.FoodDetailsScreen}/$foodId")
                    })
            }

            composable(route = "${Route.FoodDetailsScreen}/{foodId}") { backStackEntry ->
                val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
                FoodDetailsScreen(foodId = foodId)
            }

            composable(route = "RecipeTypeScreen/{recipeType}") { backStackEntry ->
                val viewModel: RecipeSearchViewModel = hiltViewModel()
                val recipeType = backStackEntry.arguments?.getString("recipeType") ?: ""
                androidx.compose.runtime.LaunchedEffect(recipeType) {
                    viewModel.searchRecipes(recipeType)
                }
                val recipes = viewModel.recipes.collectAsState().value
                RecipeSearchScreen(
                    recipeType = recipeType,
                    recipes = recipes,
                    onRecipeClick = { /* TODO: Implement recipe details navigation */ }
                )
            }

        }
    }
}