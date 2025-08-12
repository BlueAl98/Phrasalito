package com.nayibit.phrasalito_presentation.screens.deckScreen

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.printToLog
import com.nayibit.phrasalito_domain.model.Deck
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class DeckScreenKtTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun showsLoadingScreen_whenStateIsLoading() {
        // Arrange
        val state = DeckStateUi(
            isLoading = true,
            decks = emptyList(),
            showModal = false
        )

        val events = MutableSharedFlow<DeckUiEvent>()

        // Act
        composeRule.setContent {
            DeckScreen(
                state = state,
                eventFlow = events,
                onEvent = {}
            )
        }

        // Assert
        composeRule.onNodeWithTag("loading_indicator").assertIsDisplayed()

    }


    @Test
    fun showsDeckList_whenDecksAvailable() {
        // Arrange
        val state = DeckStateUi(
            isLoading = false,
            decks = listOf(
                Deck(id = 1, name = "Deck 1", 12),
                Deck(id = 2, name = "Deck 2", 13)
            ),
            showModal = false
        )

        val events = MutableSharedFlow<DeckUiEvent>()

        // Act
        composeRule.setContent {
            DeckScreen(
                state = state,
                eventFlow = events,
                onEvent = {}
            )
        }

        // Assert the LazyColumn is visible
        composeRule.onNodeWithTag("deck_list").assertIsDisplayed()

        // Assert each deck item is visible
       composeRule.onNodeWithTag("deck_item_1").assertIsDisplayed()
       composeRule.onNodeWithTag("deck_item_2").assertIsDisplayed()


    }

    /*
    @Test
    fun showsModalansDissmissDialog_whenTriggerModalEvent() {
        // Make state mutable so we can update it from onEvent
        var state by mutableStateOf(
            DeckStateUi(
                isLoading = false,
                decks = emptyList(),
                showModal = false)
        )
        val events = MutableSharedFlow<DeckUiEvent>()

        composeRule.setContent {
            DeckScreen(
                state = state,
                eventFlow = events,
                onEvent = { event ->
                    when (event) {
                        is DeckUiEvent.TriggerModal -> {
                            // Simulate VM emitting show modal state
                            state = state.copy(showModal = true)
                        }
                        is DeckUiEvent.ShowModal -> {
                            state = state.copy(showModal = true)
                        }
                        is DeckUiEvent.DismissModal -> {
                            state = state.copy(showModal = false)
                        }
                        else -> {}
                    }
                }
            )
        }

        // Act — click FAB to trigger modal
      composeRule.onNodeWithContentDescription("Add").performClick()

        // Wait for recomposition
        composeRule.waitForIdle()

        // Assert
        composeRule.onNodeWithTag("deck_dialog")
            .assertIsDisplayed()

        //Simuate clicking outside the dialog
        runBlocking {events.emit(DeckUiEvent.DismissModal)}

        composeRule.waitForIdle()


        composeRule.onNodeWithTag("deck_dialog")
            .assertIsNotDisplayed()


    }
*/


}