package com.anderson.nutritionapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.anderson.nutritionapp.MainViewModel
import com.anderson.nutritionapp.presentation.analytics.LogScreenView
import com.anderson.nutritionapp.presentation.nutrition_navigator.NutritionNavigator
import com.anderson.nutritionapp.presentation.onboarding.OnBoardingScreen
import com.anderson.nutritionapp.presentation.onboarding.OnBoardingViewModel
import com.anderson.nutritionapp.presentation.user_auth.AuthViewModel
import com.anderson.nutritionapp.presentation.user_auth.LoginScreen
import com.anderson.nutritionapp.presentation.user_auth.RegisterScreen

@Composable
fun NavGraph(
    startDestination: String
) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {

        composable(route = Route.LoginScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            LogScreenView("LoginScreen")
            LoginScreen(onLogin = { email, password ->
                kotlinx.coroutines.suspendCancellableCoroutine<String?> { cont ->
                    authViewModel.login(email, password) { success, _, errorMsg ->
                        if (success) {
                            navController.navigate(Route.NutritionNavigation.route) {
                                popUpTo(Route.AppStartNavigation.route) { inclusive = true }
                            }
                            cont.resume(null, null)
                        } else {
                            cont.resume(errorMsg ?: "Invalid email or password", null)
                        }
                    }
                }
            }, onRegister = {
                navController.navigate(Route.RegisterScreen.route)
            })
        }

        composable(route = Route.RegisterScreen.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val mainViewModel: MainViewModel = hiltViewModel()
            val startDestination =
                mainViewModel.startDestination // observe as state if mutableStateOf

            var shouldNavigate by remember { mutableStateOf(false) }
            LogScreenView("RegisterScreen")
            RegisterScreen(onRegister = { email, password ->
                kotlinx.coroutines.suspendCancellableCoroutine<String?> { cont ->
                    authViewModel.register(email, password) { success, _ ->
                        if (success) {
                            mainViewModel.refreshStartDestination()
                            shouldNavigate = true
                            cont.resume(null, null)
                        } else {
                            cont.resume("Registration failed", null)
                        }
                    }
                }
            }, onBack = { navController.popBackStack() })

            if (shouldNavigate) {
                LaunchedEffect(startDestination) {
                    navController.navigate(startDestination) {
                        popUpTo(Route.RegisterScreen.route) { inclusive = true }
                    }
                    shouldNavigate = false
                }
            }
        }

        navigation(
            route = Route.AppStartNavigation.route, startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                LogScreenView("OnBoardingScreen")
                OnBoardingScreen(
                    event = viewModel::onEvent, onFinished = {
                        navController.navigate(Route.NutritionNavigation.route) {
                            popUpTo(Route.OnBoardingScreen.route) { inclusive = true }
                        }
                    })
            }

        }

        navigation(
            route = Route.NutritionNavigation.route,
            startDestination = Route.NutritionNavigationScreen.route
        ) {
            composable(route = Route.NutritionNavigationScreen.route) {
                NutritionNavigator()
            }
        }


    }
}