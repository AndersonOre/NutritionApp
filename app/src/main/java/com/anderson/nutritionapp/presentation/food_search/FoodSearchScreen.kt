package com.anderson.nutritionapp.presentation.food_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.anderson.nutritionapp.data.remote.dto.FoodSearchResponseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodSearchScreen(
    categoryName: String,
    foodSearchResults: FoodSearchResponseModel?,
    onFoodClick: (foodId: String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Results for: $categoryName") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            foodSearchResults?.let { results ->

                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(results.foods_search.results.food) { food ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable(
                                    onClick = { onFoodClick(food.food_id) },
                                ),

                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                food.food_images?.food_image?.let { images ->
                                    if (images.isNotEmpty()) {
                                        val middleIndex = images.size / 2
                                        val imageUrl = images[middleIndex].image_url
                                        AsyncImage(
                                            model = imageUrl,
                                            contentDescription = food.food_name,
                                            modifier = Modifier
                                                .size(80.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(
                                        text = food.food_name,
                                        style = MaterialTheme.typography.bodyLarge
                                    )
                                    Text(
                                        text = "Calories: ${food.servings?.serving?.firstOrNull()?.calories ?: "N/A"}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }
                }
            } ?: Text(
                text = "No results found.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}