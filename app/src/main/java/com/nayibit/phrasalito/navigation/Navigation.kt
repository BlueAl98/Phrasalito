package com.nayibit.phrasalito.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ie.feature_startscreen.presentation.startScreen.FeatureStartScreen
import com.nayibit.feature_categories.presentation.categoryScreen.FeatureCategoryScreen
import com.nayibit.feature_deckscreen.presentation.deckScreen.FeatureDeckScreen
import com.nayibit.feature_languages.presentation.languageScreen.FeatureLanguageScreen
import com.nayibit.feature_phrases.presentation.phraseScreen.FeaturePhraseScreen
import com.nayibit.utils.SelectScreen


@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.StartScreen
    ) {

        composable <Routes.StartScreen>{
            FeatureStartScreen { screen ->
                when(screen){
                    SelectScreen.START_SCREEN -> {}
                    SelectScreen.LANGUAGE_SCREEN -> {
                        navController.navigate(Routes.LanguageScreen) {
                            popUpTo<Routes.StartScreen> { inclusive = true }
                        }
                    }
                    SelectScreen.CATEGORIE_SCREEN -> {
                        navController.navigate(Routes.CategoryScreen) {
                            popUpTo<Routes.StartScreen> { inclusive = true }
                        }
                    }
                }


            }
        }

         composable <Routes.CategoryScreen>{
             FeatureCategoryScreen(
                 navigation = { id ->
                     navController.navigate(Routes.DeckScreen(id))
                 },
                 onChangeLanguage = {
                     navController.navigate(Routes.LanguageScreen){
                       popUpTo<Routes.CategoryScreen> { inclusive = true }
                     }
                 }
             )
        }

        composable<Routes.DeckScreen>{
            FeatureDeckScreen{ idDeck ->
                navController.navigate(Routes.PhraseScreen(idDeck))
            }
        }

        composable<Routes.PhraseScreen> {
             FeaturePhraseScreen {
                 navController.navigate(Routes.LanguageScreen)
            }
        }

        composable<Routes.LanguageScreen> {
            FeatureLanguageScreen { _ ->
                navController.navigate(Routes.CategoryScreen)
            }
        }

    }
}