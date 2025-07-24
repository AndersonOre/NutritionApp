package com.anderson.nutritionapp.presentation.nutrition_navigator.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults.IconSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anderson.nutritionapp.R
import com.anderson.nutritionapp.ui.theme.NutritionAppTheme


data class BottomNavigationItem(
    @DrawableRes val icon: Int,
    val text: String
)

@Composable
fun NutritionBottomNavigation(
    items: List<BottomNavigationItem>,
    selected: Int,
    onItemSelected: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 10.dp,

            )
    ) {
        NavigationBar(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .fillMaxWidth(),
            containerColor = MaterialTheme.colorScheme.surface,
            tonalElevation = 5.dp
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == selected,
                    onClick = { onItemSelected(index) },
                    icon = {
                        Column(horizontalAlignment = CenterHorizontally) {
                            Icon(
                                painter = painterResource(id = item.icon),
                                contentDescription = null,
                                modifier = Modifier.size(IconSize),
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = item.text,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        unselectedIconColor = colorResource(id = R.color.body),
                        unselectedTextColor = colorResource(id = R.color.body),
                        indicatorColor = MaterialTheme.colorScheme.surface,
                    ),
                )
            }
        }
    }
}

@Preview
@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun NewsBottomNavigationPreview() {
    NutritionAppTheme(dynamicColor = false) {
        NutritionBottomNavigation(
            items = listOf(
                BottomNavigationItem(icon = R.drawable.ic_home, text = "Home"),
                BottomNavigationItem(icon = R.drawable.ic_search, text = "Search"),
                BottomNavigationItem(icon = R.drawable.ic_bookmark, text = "Bookmark"),
            ),
            selected = 0,
            onItemSelected = {}
        )
    }
}