package com.anderson.nutritionapp.presentation.recipe_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.domain.usecase.nutrition.GetRecipeDetails
import com.anderson.nutritionapp.data.remote.dto.RecipeDetailsResponseModel
import com.anderson.nutritionapp.domain.usecase.nutrition.NutritionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RecipeDetailsViewModel @Inject constructor(
    private val nutritionUseCases: NutritionUseCases
) : ViewModel() {

    private val _recipeDetails = MutableStateFlow<RecipeDetailsResponseModel?>(null)
    val recipeDetails: StateFlow<RecipeDetailsResponseModel?> = _recipeDetails

    fun loadRecipeDetails(recipeId: String) {
        viewModelScope.launch {
            _recipeDetails.value = nutritionUseCases.getRecipeDetails(recipeId)
        }
    }
}