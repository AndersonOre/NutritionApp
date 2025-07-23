package com.anderson.nutritionapp.presentation.food_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun FoodDetailsScreen(
    foodId: String,
    viewModel: FoodDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(foodId) {
        viewModel.loadFoodDetails(foodId)
    }

    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        state.error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.error}")
            }
        }
        state.food != null -> {
            val food = state.food
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                item {
                    if (food != null) {
                        food.food_images?.food_image?.firstOrNull()?.image_url?.let { url ->
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                if (food != null) {
                                    AsyncImage(
                                        model = url,
                                        contentDescription = food.food_name,
                                        modifier = Modifier.size(180.dp)
                                    )
                                }
                            }
                            Spacer(Modifier.height(16.dp))
                        }
                    }
                    if (food != null) {
                        Text(food.food_name, style = MaterialTheme.typography.headlineSmall)
                    }

                    Spacer(Modifier.height(8.dp))
                    if (food != null) {
                        food.food_sub_categories?.food_sub_category?.let { subs ->
                            if (subs.isNotEmpty()) {
                                Text(
                                    "Subcategories: ${subs.joinToString()}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    if (food != null) {
                        food.food_attributes?.allergens?.allergen?.let { allergens ->
                            if (allergens.isNotEmpty()) {
                                Text(
                                    "Allergens: ${allergens.joinToString { it.name }}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                    if (food != null) {
                        food.food_attributes?.preferences?.preference?.let { prefs ->
                            if (prefs.isNotEmpty()) {
                                Text(
                                    "Preferences: ${prefs.joinToString { it.name }}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }
                    }
                }
                if (food != null) {
                    food.servings?.serving?.forEach { serving ->
                        item {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(Modifier.padding(12.dp)) {
                                    Text(
                                        "Serving: ${serving.serving_description}",
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                    Text("Calories: ${serving.calories}")
                                    Text("Carbs: ${serving.carbohydrate}g, Protein: ${serving.protein}g, Fat: ${serving.fat}g")
                                    Text("Saturated: ${serving.saturated_fat}g, Polyunsaturated: ${serving.polyunsaturated_fat}g, Monounsaturated: ${serving.monounsaturated_fat}g")
                                    Text("Cholesterol: ${serving.cholesterol}mg, Sodium: ${serving.sodium}mg, Potassium: ${serving.potassium}mg")
                                    Text("Fiber: ${serving.fiber}g, Sugar: ${serving.sugar}g")
                                    Text("Vitamin A: ${serving.vitamin_a}, Vitamin C: ${serving.vitamin_c}")
                                    Text("Calcium: ${serving.calcium}mg, Iron: ${serving.iron}mg")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}