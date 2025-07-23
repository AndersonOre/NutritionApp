package com.anderson.nutritionapp.presentation.food_details


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.data.local.FoodDao
import com.anderson.nutritionapp.data.remote.dto.Food
import com.anderson.nutritionapp.domain.usecase.nutrition.NutritionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FoodDetailsState(
    val isLoading: Boolean = false,
    val food: Food? = null,
    val error: String? = null
)

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    private val nutritionUseCases: NutritionUseCases,
    private val foodDao: FoodDao
) : ViewModel() {

    private val _state = MutableStateFlow(FoodDetailsState())
    val state: StateFlow<FoodDetailsState> = _state
    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite


    fun loadFoodDetails(foodId: String) {
        viewModelScope.launch {
            _state.value = FoodDetailsState(isLoading = true)
            try {
                val response = nutritionUseCases.getFoodById(foodId)
                _state.value = FoodDetailsState(food = response?.food)
            } catch (e: Exception) {
                _state.value = FoodDetailsState(error = e.message)
            }
        }
    }

    fun checkIfFavorite(foodId: String) {
        viewModelScope.launch {
            _isFavorite.value = foodDao.isFavorite(foodId)
        }
    }

    fun addOrRemoveFavorite(food: Food) {
        viewModelScope.launch {
            if (_isFavorite.value) {
                foodDao.delete(food)
            } else {
                foodDao.upsert(food)
            }
            _isFavorite.value = !_isFavorite.value
        }
    }
}