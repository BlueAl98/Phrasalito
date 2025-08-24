package com.nayibit.phrasalito_presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckScreen
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckViewModel
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseScreen
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseViewModel
import com.nayibit.phrasalito_presentation.screens.startScreen.StartScreen
import kotlinx.serialization.Serializable


//Argumentos

@Serializable
object DeckScreen

@Serializable
data class PhraseScreenNav(val idDeck: Int)

@Serializable
object StartScreen

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = StartScreen
    ) {

      composable<StartScreen> {
          StartScreen()
      }


     composable <DeckScreen>{
       val viewModel: DeckViewModel = hiltViewModel()
       val state by viewModel.state.collectAsStateWithLifecycle()

         DeckScreen(
             state = state,
             eventFlow = viewModel.eventFlow,
             onEvent = viewModel::onEvent
         ){ idDeck ->
            navController.navigate(PhraseScreenNav(idDeck))
         }
     }

     composable<PhraseScreenNav> {

        val  viewModel: PhraseViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

         PhraseScreen(
             state = state,
             eventFlow = viewModel.eventFlow,
             onEvent = viewModel::onEvent,
             navigation = {}
         )
     }

    }
}