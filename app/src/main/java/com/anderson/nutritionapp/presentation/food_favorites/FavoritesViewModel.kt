package com.anderson.nutritionapp.presentation.food_favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anderson.nutritionapp.data.local.FoodDao
import com.anderson.nutritionapp.data.remote.dto.Food
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    foodDao: FoodDao
) : ViewModel() {
    val favorites: StateFlow<List<Food>> = foodDao.getFoods()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}