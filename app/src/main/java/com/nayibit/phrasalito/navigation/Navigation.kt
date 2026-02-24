package com.nayibit.phrasalito.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ie.feature_startscreen.presentation.startScreen.FeatureStartScreen
import com.nayibit.feature_categories.presentation.categoryScreen.FeatureCategoryScreen
import com.nayibit.feature_deckscreen.presentation.deckScreen.FeatureDeckScreen


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
            FeatureDeckScreen()
        }

    }
}