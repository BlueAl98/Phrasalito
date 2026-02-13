package com.nayibit.phrasalito.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ie.feature_startscreen.presentation.startScreen.FeatureStartScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.DeckScreen
    ) {

        composable <Routes.DeckScreen>{

           FeatureStartScreen {
               println("BUKELE")
           }

         /*   val viewModel: DeckViewModel = hiltViewModel()
            val state by viewModel.state.collectAsStateWithLifecycle()

            DeckScreen(
                state = state,
                eventFlow = viewModel.eventFlow,
                onEvent = viewModel::onEvent,
                navigationToPhrases = { idDeck, lngCode ->
                    navController.navigate(PhraseScreenNav(idDeck, lngCode))
                }
            )*/
        }

    }
}