package com.nayibit.phrasalito.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ie.feature_startscreen.presentation.startScreen.FeatureStartScreen
import com.nayibit.feature_categories.presentation.categoryScreen.FeatureCategoryScreen


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.StartScreen
    ) {


        composable <Routes.StartScreen>{
            FeatureStartScreen {
                navController.navigate(Routes.CategoryScreen)
            }
        }

            composable <Routes.CategoryScreen>{
             FeatureCategoryScreen { id ->
              navController.navigate(Routes.DeckScreen)
            }
        }

        composable<Routes.DeckScreen>{
            Box(Modifier.fillMaxSize().background(Color.Red))
        }

    }
}