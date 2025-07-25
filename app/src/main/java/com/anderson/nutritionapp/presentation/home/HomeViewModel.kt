package com.anderson.nutritionapp.presentation.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.data.remote.dto.FoodSearchResponseModel
import com.anderson.nutritionapp.data.remote.dto.RecipeModel
import com.anderson.nutritionapp.data.remote.dto.RecipesList
import com.anderson.nutritionapp.domain.usecase.nutrition.NutritionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val nutritionUseCases: NutritionUseCases
): ViewModel() {

    val food_categories = nutritionUseCases.getFoodCategories()
    private val _recipeTypes = MutableStateFlow<List<String>>(emptyList())
    val recipeTypes: StateFlow<List<String>> = _recipeTypes

    init {
        viewModelScope.launch {
            food_categories.collectLatest { categories ->
                Log.d("HomeViewModel", "Fetched categories: $categories")
            }
        }

        viewModelScope.launch {
            nutritionUseCases.getRecipeTypes().collectLatest { types ->
                _recipeTypes.value = types
            }
        }

        fetchRandomRecipes();
    }

    private val _foodSearchResults = MutableStateFlow<FoodSearchResponseModel?>(null)
    val foodSearchResults: StateFlow<FoodSearchResponseModel?> = _foodSearchResults

    fun searchFoods(searchExpression: String, pageNumber: Int? = null, maxResults: Int? = null) {
        viewModelScope.launch {
            nutritionUseCases.searchFoods(searchExpression, pageNumber, maxResults)
                .collect { result ->
                    _foodSearchResults.value = result
                }
        }
    }

    private val _randomRecipes = MutableStateFlow<List<RecipesList>>(emptyList())
    val randomRecipes: StateFlow<List<RecipesList>> = _randomRecipes

    fun fetchRandomRecipes() {
        viewModelScope.launch {
            nutritionUseCases.searchRecipes("", 10,"pasta").collect { response ->
                _randomRecipes.value = listOf(response.recipes)
            }
        }
    }




}