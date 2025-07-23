package com.anderson.nutritionapp.presentation.common

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anderson.nutritionapp.domain.model.FoodCategoryModel

@Composable
fun FoodCategoryList(
    categories: List<FoodCategoryModel>,
    onCategoryClick: (FoodCategoryModel) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
    ) {
        items(categories) { category ->
            FoodCategoryCard(
                category = category.name.orEmpty(),
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Composable
@Preview
fun PreviewFoodCategoryList() {
    FoodCategoryList(
        categories = listOf(
            FoodCategoryModel("Fish description", "1", "Fish"),
            FoodCategoryModel("Fruit description", "2", "Fruit"),
            FoodCategoryModel("Meat description", "3", "Meat"),
            FoodCategoryModel("Vegetables description", "4", "Vegetables")
        ),
        onCategoryClick = {}
    )
}