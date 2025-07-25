package com.anderson.nutritionapp.presentation.nutrition_navigator

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.anderson.nutritionapp.R
import com.anderson.nutritionapp.presentation.analytics.LogScreenView
import com.anderson.nutritionapp.presentation.food_details.FoodDetailsScreen
import com.anderson.nutritionapp.presentation.food_favorites.FavoritesScreen
import com.anderson.nutritionapp.presentation.food_search.FoodSearchScreen
import com.anderson.nutritionapp.presentation.home.HomeScreen
import com.anderson.nutritionapp.presentation.home.HomeViewModel
import com.anderson.nutritionapp.presentation.navgraph.Route
import com.anderson.nutritionapp.presentation.nutrition_navigator.components.BottomNavigationItem
import com.anderson.nutritionapp.presentation.nutrition_navigator.components.NutritionBottomNavigation
import com.anderson.nutritionapp.presentation.recipe_details.RecipeDetailsScreen
import com.anderson.nutritionapp.presentation.recipe_search.RecipeSearchScreen
import com.anderson.nutritionapp.presentation.recipe_search.RecipeSearchViewModel
import com.anderson.nutritionapp.presentation.user_auth.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NutritionNavigator() {

    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
//            BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
            BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableStateOf(0)
    }
    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
//        Route.SearchScreen.route -> 1
        Route.FoodFavoritesScreen.route -> 1
        else -> 0
    }

    //Hide the bottom navigation when the user is in the details screen
    val isBottomBarVisible = remember(key1 = backStackState) {
        backStackState?.destination?.route == Route.HomeScreen.route || backStackState?.destination?.route == Route.SearchScreen.route || backStackState?.destination?.route == Route.FoodFavoritesScreen.route
    }

    Scaffold(modifier = Modifier.fillMaxSize(), bottomBar = {
        if (isBottomBarVisible) {
            NutritionBottomNavigation(
                items = bottomNavigationItems, selected = selectedItem, onItemSelected = { index ->
                    when (index) {
                        0 -> navigateToTab(
                            navController = navController, route = Route.HomeScreen.route
                        )

//                        1 -> navigateToTab(
//                            navController = navController, route = Route.SearchScreen.route
//                        )

                        1 -> navigateToTab(
                            navController = navController, route = Route.FoodFavoritesScreen.route
                        )
                    }
                })
        }
    }) {
        val bottomPadding = it.calculateBottomPadding()
        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {

                val viewModel: HomeViewModel = hiltViewModel()
                val categories = viewModel.food_categories.collectAsState(initial = emptyList())
                val recipeTypes = viewModel.recipeTypes.collectAsState(initial = emptyList())
                val randomRecipes = viewModel.randomRecipes.collectAsState(initial = emptyList())
                LogScreenView("HomeScreen")
                HomeScreen(
                    categories = categories.value,
                    recipes = recipeTypes.value,
                    randomRecipes = randomRecipes.value,
                    onCategoryClick = { category ->
                        navController.navigate("${Route.FoodSearchScreen}/${category.name}")
                    },
                    onRecipeClick = { recipeType ->
                        navController.navigate("RecipeTypeScreen/$recipeType")
                    },
                    onRandomRecipeClick = { recipeId ->
                        navController.navigate("${Route.RecipeDetailsScreen}/$recipeId")
                    },
                    authViewModel = hiltViewModel<AuthViewModel>(),
                )

            }
            composable(route = "${Route.FoodSearchScreen}/{categoryName}") { backStackEntry ->
                val viewModel: HomeViewModel = hiltViewModel()
                val categoryName = backStackEntry.arguments?.getString("categoryName") ?: ""
                LaunchedEffect(categoryName) {
                    viewModel.searchFoods(categoryName)
                }
                val foodSearchResults = viewModel.foodSearchResults.collectAsState().value
                LogScreenView("FoodSearchScreen: $categoryName")
                FoodSearchScreen(
                    categoryName = categoryName,
                    foodSearchResults = foodSearchResults,
                    onFoodClick = { foodId ->
                        navController.navigate("${Route.FoodDetailsScreen}/$foodId")
                    },
                    onBackClick = { navController.popBackStack() })
            }

            composable(route = "${Route.FoodDetailsScreen}/{foodId}") { backStackEntry ->
                val foodId = backStackEntry.arguments?.getString("foodId") ?: ""
                LogScreenView("FoodDetailsScreen: $foodId")
                FoodDetailsScreen(
                    foodId = foodId,
                    onBackClick = { navController.popBackStack() }
                )
            }

            composable(route = "RecipeTypeScreen/{recipeType}") { backStackEntry ->
                val viewModel: RecipeSearchViewModel = hiltViewModel()
                val recipeType = backStackEntry.arguments?.getString("recipeType") ?: ""
                LaunchedEffect(recipeType) {
                    viewModel.searchRecipes(recipeType)
                }
                val recipes = viewModel.recipes.collectAsState().value
                LogScreenView("RecipeSearchScreen: $recipeType")
                RecipeSearchScreen(
                    recipeType = recipeType, recipes = recipes,
                    onRecipeClick = { recipeId ->
                        navController.navigate("${Route.RecipeDetailsScreen}/$recipeId")
                    },
                    onBackClick = { navController.popBackStack() })
            }
            composable(route = Route.SearchScreen.route) {
                // Replace with your actual SearchScreen Composable
                Text("Search Screen Placeholder")
            }

            composable(route = Route.FoodFavoritesScreen.route) {
                OnBackClickStateSaver(navController = navController)
                LogScreenView("FavoritesScreen")
                FavoritesScreen(
                    onFoodClick = { foodId ->
                        navController.navigate("${Route.FoodDetailsScreen}/$foodId")
                    },
                    onBackClick = {
                        navigateToTab(navController, Route.HomeScreen.route)
                    })
            }

            composable(route = "${Route.RecipeDetailsScreen}/{recipeId}") { backStackEntry ->
                val recipeId = backStackEntry.arguments?.getString("recipeId") ?: ""
                LogScreenView("RecipeDetailsScreen: $recipeId")
                RecipeDetailsScreen(recipeId = recipeId,  onBackClick = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun OnBackClickStateSaver(navController: NavController) {
    BackHandler(true) {
        navigateToTab(
            navController = navController, route = Route.HomeScreen.route
        )
    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { screen_route ->
            popUpTo(screen_route) {
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

