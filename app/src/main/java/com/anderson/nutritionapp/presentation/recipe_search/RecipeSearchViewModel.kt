package com.anderson.nutritionapp.presentation.recipe_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.data.remote.dto.RecipeSearchResponseModel
import com.anderson.nutritionapp.domain.usecase.nutrition.NutritionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeSearchViewModel @Inject constructor(
    private val nutritionUseCases: NutritionUseCases
) : ViewModel() {

    private val _recipes = MutableStateFlow<RecipeSearchResponseModel?>(null)
    val recipes: StateFlow<RecipeSearchResponseModel?> = _recipes

    fun searchRecipes(recipeType: String) {
        viewModelScope.launch {
            nutritionUseCases.searchRecipes(recipeType,15,"").collect { result ->
                _recipes.value = result
            }
        }
    }
}