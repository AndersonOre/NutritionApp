package com.anderson.nutritionapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.domain.usecase.app_entry.AppEntryUseCases
import com.anderson.nutritionapp.presentation.navgraph.Route
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel(){

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.LoginScreen.route)
        private set

    init {
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                // Not logged in: show login
                startDestination = Route.LoginScreen.route
                splashCondition = false
            } else {
                // Logged in: check onboarding
                appEntryUseCases.readAppEntry().onEach { onboardingDone ->
                    startDestination = if (onboardingDone) {
                        Route.NutritionNavigation.route
                    } else {
                        Route.AppStartNavigation.route // This will show onboarding
                    }
                    splashCondition = false
                }.launchIn(this)
            }
        }
    }

    // In MainViewModel.kt
    fun refreshStartDestination() {
        viewModelScope.launch {
            val currentUser = FirebaseAuth.getInstance().currentUser
            if (currentUser == null) {
                startDestination = Route.LoginScreen.route
            } else {
                appEntryUseCases.readAppEntry().onEach { onboardingDone ->
                    startDestination = if (onboardingDone) {
                        Route.NutritionNavigation.route
                    } else {
                        Route.AppStartNavigation.route
                    }
                }.launchIn(this)
            }
            splashCondition = false
        }
    }
}