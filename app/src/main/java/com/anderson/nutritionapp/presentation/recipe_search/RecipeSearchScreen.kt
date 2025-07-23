package com.anderson.nutritionapp.presentation.recipe_search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import coil.compose.AsyncImage
import com.anderson.nutritionapp.data.remote.dto.RecipeSearchResponseModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeSearchScreen(
    recipeType: String,
    recipes: RecipeSearchResponseModel?,
    onRecipeClick: (recipeId: String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Recipes: $recipeType") }
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
            recipes?.let { results ->
                Spacer(modifier = Modifier.height(10.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(results.recipes.recipe) { recipe ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onRecipeClick(recipe.recipe_id) },
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                recipe.recipe_image?.let { imageUrl ->
                                    AsyncImage(
                                        model = imageUrl,
                                        contentDescription = recipe.recipe_name,
                                        modifier = Modifier.size(80.dp).clip(RoundedCornerShape(8.dp))
                                    )
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(recipe.recipe_name, style = MaterialTheme.typography.bodyLarge)
                                    Text(
                                        "Calories: ${recipe.recipe_nutrition.calories}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                    Text(
                                        recipe.recipe_description,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray,
                                        maxLines = 2
                                    )
                                }
                            }
                        }
                    }
                }
            } ?: Text(
                text = "No recipes found.",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}