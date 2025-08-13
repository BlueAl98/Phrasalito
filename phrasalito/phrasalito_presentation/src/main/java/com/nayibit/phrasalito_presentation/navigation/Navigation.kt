package com.nayibit.phrasalito_presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckScreen
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckViewModel
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseScreen
import kotlinx.serialization.Serializable


//Argumentos

@Serializable
object DeckScreen

@Serializable
data class PhraseScreenNav(val idDeck: Int)

@Composable
fun Navigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = DeckScreen
    ) {
     composable <DeckScreen>{
       val viewModel: DeckViewModel = hiltViewModel()
       val state by viewModel.state.collectAsStateWithLifecycle()

         DeckScreen(
             state = state,
             eventFlow = viewModel.eventFlow,
             onEvent = viewModel::onEvent
         ){
            navController.navigate(PhraseScreenNav(1))
         }
     }

     composable<PhraseScreenNav> {
         val args = it.toRoute<PhraseScreenNav>()
         PhraseScreen(args.idDeck)
     }

    }
}