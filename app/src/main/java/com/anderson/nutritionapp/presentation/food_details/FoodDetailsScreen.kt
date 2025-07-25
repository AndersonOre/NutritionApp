package com.anderson.nutritionapp.presentation.food_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailsScreen(
    foodId: String,
    onBackClick: () -> Unit = {},
    viewModel: FoodDetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val isFavorite by viewModel.isFavorite.collectAsState()

    LaunchedEffect(foodId) {
        viewModel.loadFoodDetails(foodId)
        viewModel.checkIfFavorite(foodId)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Food Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { state.food?.let { viewModel.addOrRemoveFavorite(it) } }
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                state.error != null -> {
                    Text(
                        "Error: ${state.error}",
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                state.food != null -> {
                    val food = state.food
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 24.dp, vertical = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp)
                    ) {
                        item {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                ElevatedCard(
                                    shape = RoundedCornerShape(12.dp),
                                    colors = CardDefaults.elevatedCardColors(
                                        containerColor = MaterialTheme.colorScheme.surface
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                ) {
                                    AsyncImage(
                                        model = food?.food_images?.food_image?.firstOrNull()?.image_url,
                                        contentDescription = food?.food_name,
                                        contentScale = ContentScale.Fit,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                }
                                Spacer(Modifier.height(12.dp))
                                Text(
                                    food?.food_name.orEmpty(),
                                    style = MaterialTheme.typography.headlineSmall
                                )
                                Spacer(Modifier.height(8.dp))
                            }
                        }

                        food?.servings?.serving?.forEach { serving ->
                            item {
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                                ) {
                                    Column(Modifier.padding(16.dp)) {
                                        Text(
                                            "Serving: ${serving.serving_description}",
                                            style = MaterialTheme.typography.titleMedium
                                        )
                                        Spacer(Modifier.height(6.dp))
                                        Text("Calories: ${serving.calories}", style = MaterialTheme.typography.bodyMedium)
                                        Text("Carbs: ${serving.carbohydrate}g  Protein: ${serving.protein}g  Fat: ${serving.fat}g", style = MaterialTheme.typography.bodySmall)
                                        Text("Fiber: ${serving.fiber}g  Sugar: ${serving.sugar}g", style = MaterialTheme.typography.bodySmall)
                                        Text("Sodium: ${serving.sodium}mg  Potassium: ${serving.potassium}mg", style = MaterialTheme.typography.bodySmall)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}