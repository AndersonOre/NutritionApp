package com.anderson.nutritionapp.unit_test.recipe_details

import com.anderson.nutritionapp.data.remote.dto.*
import com.anderson.nutritionapp.domain.model.FoodCategoryModel
import com.anderson.nutritionapp.domain.repository.NutritionRepository
import com.anderson.nutritionapp.domain.usecase.nutrition.*
import com.anderson.nutritionapp.presentation.recipe_details.RecipeDetailsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FakeNutritionRepository : NutritionRepository {
    override suspend fun getRecipeDetails(recipeId: String): RecipeDetailsResponseModel {
        return RecipeDetailsResponseModel(
            recipe = RecipeDetail(
                recipe_id = recipeId,
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
        )
    }

    override fun getFoodCategories(): Flow<List<FoodCategoryModel>> = emptyFlow()
    override fun searchFoods(
        searchExpression: String,
        pageNumber: Int?,
        maxResults: Int?
    ): Flow<FoodSearchResponseModel> = emptyFlow()

    override suspend fun getFoodById(foodId: String): FoodByIdResponseModel? = null
    override fun getRecipeTypes(): Flow<List<String>> = emptyFlow()
    override fun searchRecipes(
        recipeType: String,
        maxResults: Int
    ): Flow<RecipeSearchResponseModel> = emptyFlow()
}

val fakeNutritionRepository = FakeNutritionRepository()

val fakeNutritionUseCases = NutritionUseCases(
    getFoodCategories = GetFoodCategories(fakeNutritionRepository),
    getFoodById = GetFoodById(fakeNutritionRepository),
    searchFoods = SearchFoods(fakeNutritionRepository),
    getRecipeTypes = GetRecipeTypes(fakeNutritionRepository),
    searchRecipes = SearchRecipes(fakeNutritionRepository),
    getRecipeDetails = GetRecipeDetails(fakeNutritionRepository)
)


@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailsViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: RecipeDetailsViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = RecipeDetailsViewModel(fakeNutritionUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun loadRecipeDetails_updatesRecipeDetailsState() = runTest(testDispatcher) {
        viewModel.loadRecipeDetails("123")
        testDispatcher.scheduler.advanceUntilIdle() // Wait for coroutine to finish
        val result = viewModel.recipeDetails.value
        assertEquals("123", result?.recipe?.recipe_id)
        assertEquals("Test Recipe", result?.recipe?.recipe_name)
        assertEquals("5", result?.recipe?.rating)
    }
}