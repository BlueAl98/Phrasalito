package com.nayibit.phrasalito_presentation.screens.deckScreen

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nayibit.phrasalito_presentation.ui.theme.PhrasalitoTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeckScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val fakeDecks = listOf(
        DeckUI(id = 1, name = "English Deck", numCards = 5),
        DeckUI(id = 2, name = "Spanish Deck", numCards = 3)
    )

    @Test
    fun deckList_isDisplayed_whenStateHasDecks() {
        val state = DeckStateUi(decks = fakeDecks, isLoading = false)
        val eventFlow = MutableSharedFlow<DeckUiEvent>()
        var eventCalled: DeckUiEvent? = null

        composeRule.setContent {
            PhrasalitoTheme {
                DeckScreen(
                    state = state,
                    eventFlow = eventFlow,
                    onEvent = { eventCalled = it },
                    navigationToPhrases = { _, _ -> }
                )
            }
        }

        // ✅ Assert LazyColumn exists
        composeRule.onNodeWithTag("deck_list").assertExists()

        // ✅ Assert each deck name appears
        composeRule.onNodeWithText("English Deck").assertIsDisplayed()
        composeRule.onNodeWithText("Spanish Deck").assertIsDisplayed()
    }

    @Test
    fun clickingFloatingButton_triggersShowModalEvent() {
        val state = DeckStateUi()
        val eventFlow = MutableSharedFlow<DeckUiEvent>()
        var eventCalled: DeckUiEvent? = null

        composeRule.setContent {
            PhrasalitoTheme {
                DeckScreen(
                    state = state,
                    eventFlow = eventFlow,
                    onEvent = { eventCalled = it },
                    navigationToPhrases = { _, _ -> }
                )
            }
        }

        // ✅ Click FAB
        composeRule.onNodeWithContentDescription("Add").performClick()

        // ✅ Verify event emitted
        assert(eventCalled is DeckUiEvent.ShowModal)
        val modal = eventCalled as DeckUiEvent.ShowModal
        assert(modal.type == BodyDeckModalEnum.BODY_INSERT_DECK)
    }
}


