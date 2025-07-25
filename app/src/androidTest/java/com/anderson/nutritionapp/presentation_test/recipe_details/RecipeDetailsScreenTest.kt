package com.anderson.nutritionapp.presentation_test.recipe_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.anderson.nutritionapp.data.remote.dto.*
import org.junit.Rule
import org.junit.Test

class RecipeDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun recipeDetailsScreen_showsRecipeNameAndRating() {
        val recipe = RecipeDetail(
            recipe_id = "123",
            recipe_name = "Test Recipe",
            recipe_description = "Test Description",
            recipe_url = "",
            recipe_images = null,
            recipe_types = null,
            recipe_categories = null,
            ingredients = null,
            directions = null,
            number_of_servings = "2",
            preparation_time_min = "10",
            cooking_time_min = "20",
            grams_per_portion = null,
            rating = "5",
            serving_sizes = null
        )
        val details = RecipeDetailsResponseModel(recipe)

        composeTestRule.setContent {
            TestRecipeDetailsScreen(details)
        }

        composeTestRule.onNodeWithText("Test Recipe").assertExists()
        composeTestRule.onNodeWithText("Rating: 5").assertExists()
    }
}

@Composable
fun TestRecipeDetailsScreen(details: RecipeDetailsResponseModel) {
    val recipe = details.recipe
    Column {
        Text(text = recipe.recipe_name)
        recipe.rating?.let {
            Text(text = "Rating: $it")
        }
    }
}