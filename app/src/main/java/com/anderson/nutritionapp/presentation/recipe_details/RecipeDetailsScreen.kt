package com.anderson.nutritionapp.presentation.recipe_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.anderson.nutritionapp.data.remote.dto.Ingredient
import com.anderson.nutritionapp.data.remote.dto.Direction

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    onBackClick: () -> Unit = {},
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(recipeId) { viewModel.loadRecipeDetails(recipeId) }
    val recipeDetails = viewModel.recipeDetails.collectAsState().value

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Recipe Details", style = MaterialTheme.typography.titleLarge) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        if (recipeDetails == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
            return@Scaffold
        }

        val recipe = recipeDetails.recipe

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    val imageUrl = recipe.recipe_images?.recipe_image?.firstOrNull()
                    if (imageUrl != null) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(180.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                        ) {
                            AsyncImage(
                                model = imageUrl,
                                contentDescription = "Recipe Image",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = recipe.recipe_name,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        recipe.rating?.let { rating ->
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Rating",
                                tint = Color(0xFFFFC107), // Amber color for star
                                modifier = Modifier.size(24.dp)
                            )
                            Text(
                                text = rating.toString(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFFC107),
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(start = 4.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))

                    // Tags row
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        recipe.number_of_servings?.let {
                            AssistChip(onClick = {}, label = { Text("Servings: $it") },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFFE3F2FD),
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ), border = null )
                        }
                        recipe.preparation_time_min?.let {
                            AssistChip(onClick = {}, label = { Text("Prep: $it min") },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = Color(0xFFE8F5E9),
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ), border = null )
                        }
                        recipe.cooking_time_min?.let {
                            AssistChip(onClick = {}, label = { Text("Cook: $it min") },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor =  Color(0xFFFFF3E0),
                                    labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                                ), border = null )
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = recipe.recipe_description)
                }
            }

            val ingredients: List<Ingredient> = recipe.ingredients?.ingredient ?: emptyList()
            if (ingredients.isNotEmpty()) {
                item {
                    Text(
                        text = "Ingredients",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(ingredients) { ingredient ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = ingredient.food_name,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Text(
                                text = "${ingredient.number_of_units} ${ingredient.measurement_description}",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        ingredient.ingredient_description?.let {
                            Text(
                                text = it,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(start = 12.dp, end = 12.dp, bottom = 8.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            val directions: List<Direction> = recipe.directions?.direction ?: emptyList()
            if (directions.isNotEmpty()) {
                item {
                    Text(
                        text = "Directions",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
                items(directions) { direction ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.Top
                        ) {
                            Text(
                                text = "${direction.direction_number}.",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                text = direction.direction_description,
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Normal
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}