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
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseScreen
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.ExerciseViewModel
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseScreen
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseViewModel
import com.nayibit.phrasalito_presentation.screens.startScreen.StartScreen
import com.nayibit.phrasalito_presentation.screens.startScreen.StartViewModel
import kotlinx.serialization.Serializable


//Argumentos

@Serializable
object DeckScreen

@Serializable
data class PhraseScreenNav(val idDeck: Int)

@Serializable
data class ExerciseScreenNav(val idDeck: Int)

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
          val viewModel: StartViewModel = hiltViewModel()
          val state by viewModel.state.collectAsStateWithLifecycle()

          StartScreen(
              state = state,
              eventFlow = viewModel.eventFlow,
              onEvent = viewModel::onEvent
          ){
              navController.navigate(DeckScreen){
                  popUpTo(StartScreen) {
                      inclusive = true
                  }
              }
          }
      }


     composable <DeckScreen>{
       val viewModel: DeckViewModel = hiltViewModel()
       val state by viewModel.state.collectAsStateWithLifecycle()

         DeckScreen(
             state = state,
             eventFlow = viewModel.eventFlow,
             onEvent = viewModel::onEvent,
             navigationToPhrases = { idDeck ->
                 navController.navigate(PhraseScreenNav(idDeck))
             }
         )
}
     composable<PhraseScreenNav> {

        val  viewModel: PhraseViewModel = hiltViewModel()
        val state by viewModel.state.collectAsStateWithLifecycle()

         PhraseScreen(
             state = state,
             eventFlow = viewModel.eventFlow,
             onEvent = viewModel::onEvent,
             navigation = {
                 navController.navigate(ExerciseScreenNav(it))
             }
         )
     }

     composable <ExerciseScreenNav>{
         val viewModel: ExerciseViewModel = hiltViewModel()
         val state by viewModel.state.collectAsStateWithLifecycle()

         ExerciseScreen(
             state = state,
             eventFlow = viewModel.eventFlow,
             onEvent = viewModel::onEvent,
             navigation = {
                 navController.navigate(DeckScreen){
                     popUpTo(DeckScreen) {
                         inclusive = true
                     }
                 }
             })
     }
    }
}