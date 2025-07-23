package com.anderson.nutritionapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.domain.usecase.nutrition.NutritionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nutritionUseCases: NutritionUseCases
): ViewModel() {

    val food_categories = nutritionUseCases.getFoodCategories()

    init {
        viewModelScope.launch {
            food_categories.collectLatest { categories ->
                Log.d("HomeViewModel", "Fetched categories: $categories")
            }
        }
    }


}