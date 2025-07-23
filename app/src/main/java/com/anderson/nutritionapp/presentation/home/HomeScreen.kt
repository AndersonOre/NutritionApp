package com.anderson.nutritionapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anderson.nutritionapp.domain.model.FoodCategoryModel
import com.anderson.nutritionapp.presentation.common.FoodCategoryList

@Composable
fun HomeScreen(
    categories: List<FoodCategoryModel>,
    onCategoryClick: (FoodCategoryModel) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Categorias:",
            modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
        )
        FoodCategoryList(
            categories = categories,
            onCategoryClick = onCategoryClick
        )
    }
}

@Preview
@Composable
fun PreviewHomeScreen() {
    HomeScreen(
        categories = listOf(
            FoodCategoryModel("Fish description", "1", "Fish"),
            FoodCategoryModel("Fruit description", "2", "Fruit"),
            FoodCategoryModel("Meat description", "3", "Meat"),
            FoodCategoryModel("Vegetables description", "4", "Vegetables")
        ),
        onCategoryClick = {}
    )
}