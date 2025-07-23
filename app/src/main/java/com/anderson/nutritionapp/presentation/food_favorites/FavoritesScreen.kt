package com.anderson.nutritionapp.presentation.food_favorites

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.anderson.nutritionapp.data.remote.dto.Food
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FavoritesScreen(
    onFoodClick: (foodId: String) -> Unit = {},
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        topBar = {
           // TopAppBar(title = { Text("Favorites")})
        }
    ) { paddingValues ->
        if (favorites.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text("No favorites yet.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favorites) { food ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onFoodClick(food.food_id) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            food.food_images?.food_image?.firstOrNull()?.image_url?.let { url ->
                                AsyncImage(
                                    model = url,
                                    contentDescription = food.food_name,
                                    modifier = Modifier.size(80.dp)
                                )
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Column {
                                Text(food.food_name, style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    "Calories: ${food.servings?.serving?.firstOrNull()?.calories ?: "N/A"}",
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}