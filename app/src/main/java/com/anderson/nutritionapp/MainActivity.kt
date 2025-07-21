package com.anderson.nutritionapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.anderson.nutritionapp.domain.usecase.AppEntryUseCases
import com.anderson.nutritionapp.presentation.onboarding.OnBoardingScreen
import com.anderson.nutritionapp.presentation.onboarding.OnBoardingViewModel
import com.anderson.nutritionapp.ui.theme.NutritionAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var useCases: AppEntryUseCases
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()

        lifecycleScope.launch {
           useCases.readAppEntry().collect {
               Log.d("test", it.toString())
           }
        }
        setContent {
            NutritionAppTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    val viewModel: OnBoardingViewModel = hiltViewModel()
                    OnBoardingScreen(
                        event = viewModel::onEvent
                    )
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NutritionAppTheme {
        Greeting("Android")
    }
}