package com.anderson.nutritionapp.presentation.recipe_details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.anderson.nutritionapp.data.remote.dto.Ingredient
import com.anderson.nutritionapp.data.remote.dto.Direction

@Composable
fun RecipeDetailsScreen(
    recipeId: String,
    viewModel: RecipeDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(recipeId) {
        viewModel.loadRecipeDetails(recipeId)
    }
    val recipeDetails = viewModel.recipeDetails.collectAsState().value

    Scaffold(
        topBar = {
//            TopAppBar(
//                title = { Text("Recipe Details") }
//            )
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
                .padding(16.dp)
        ) {
            item {
                // Recipe Image
                val imageUrl = recipe.recipe_images?.recipe_image?.firstOrNull()
                if (imageUrl != null) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = "Recipe Image",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Recipe Name
                Text(
                    text = recipe.recipe_name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(text = recipe.recipe_description)
                Spacer(modifier = Modifier.height(8.dp))

                // Servings, Time, Rating
                Row(verticalAlignment = Alignment.CenterVertically) {
                    recipe.number_of_servings?.let {
                        Text(text = "Servings: $it", modifier = Modifier.padding(end = 16.dp))
                    }
                    recipe.preparation_time_min?.let {
                        Text(text = "Prep: $it min", modifier = Modifier.padding(end = 16.dp))
                    }
                    recipe.cooking_time_min?.let {
                        Text(text = "Cook: $it min")
                    }
                }
                recipe.rating?.let {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Rating: $it")
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Ingredients
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
                    Text(
                        text = "- ${ingredient.food_name}: ${ingredient.ingredient_description} (${ingredient.number_of_units} ${ingredient.measurement_description})"
                    )
                }
                item { Spacer(modifier = Modifier.height(16.dp)) }
            }

            // Directions
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
                    Text(text = "${direction.direction_number}. ${direction.direction_description}")
                }
            }
        }
    }
}