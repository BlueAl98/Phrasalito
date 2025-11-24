package com.nayibit.phrasalito_presentation.screens.deckScreen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nayibit.phrasalito_presentation.model.Language
import kotlinx.coroutines.flow.MutableSharedFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeckScreenTest {

    @get:Rule
    val composeRule = createComposeRule()


    private lateinit var eventFlow: MutableSharedFlow<DeckUiEvent>
    private val capturedEvents = mutableListOf<DeckUiEvent>()


    private fun setupScreen(
        state: DeckStateUi = createDefaultState(),
        onEvent: (DeckUiEvent) -> Unit = { capturedEvents.add(it) }
    ) {
        eventFlow = MutableSharedFlow()
        composeRule.setContent {
            Surface(modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars), color = MaterialTheme.colorScheme.background) {
            DeckScreen(
                state = state,
                eventFlow = eventFlow,
                onEvent = onEvent,
                navigationToPhrases = { _, _ -> }
            )
        }
        }
    }

    private fun createDefaultState(
        decks: List<DeckUI> = emptyList(),
        isLoading: Boolean = false,
        showModal: Boolean = false,
        showTutorial: Boolean = false,
        currentStep: Int = 0,
        bodyModal: BodyDeckModalEnum = BodyDeckModalEnum.BODY_INSERT_DECK
    ) = DeckStateUi(
        decks = decks,
        isLoading = isLoading,
        showModal = showModal,
        showTutorial = showTutorial,
        currentStep = currentStep,
        bodyModal = bodyModal
    )

    private fun createMockDeck(
        id: Int = 1,
        name: String = "Test Deck",
        isSwiped: Boolean = false
    ) = DeckUI(
        id = id,
        name = name,
        isSwiped = isSwiped,
        selectedLanguage = Language(1,"en_US", "English")
    )


    @Test
    fun deckScreen_whenEmpty_showsEmptyState() {
        setupScreen()

        composeRule.onNodeWithTag("deck_list").assertDoesNotExist()
    }

    @Test
    fun deckScreen_withDecks_showsLazyColumn() {
        val decks = listOf(
            createMockDeck(1, "Deck 1"),
            createMockDeck(2, "Deck 2")
        )
        setupScreen(state = createDefaultState(decks = decks))

        composeRule.onNodeWithTag("deck_list").assertExists()
    }

    @Test
    fun floatingActionButton_exists_and_works() {
        setupScreen(createDefaultState(showModal = true))
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithContentDescription("Add").performClick()
        assert(capturedEvents.last() is DeckUiEvent.ShowModal)
        assert((capturedEvents.last() as DeckUiEvent.ShowModal).type == BodyDeckModalEnum.BODY_INSERT_DECK)
        composeRule.onNodeWithTag("text_field_insert_deck").assertExists()
    }

    @Test
    fun show_modal_and_funcionality(){
        setupScreen(createDefaultState(showModal = true, bodyModal = BodyDeckModalEnum.BODY_INSERT_DECK))
        composeRule.onNodeWithTag("text_field_insert_deck").assertExists()
        composeRule.onNodeWithTag("text_field_insert_deck").performClick()
        composeRule.onNodeWithTag("text_field_base")
            .performTextInput("My Deck")
    }

    @Test
    fun showTutorial_and_funcionality(){
        val fakeDeck = listOf(createMockDeck(1, "Deck 1"))
        setupScreen(createDefaultState(showTutorial = true, decks = fakeDeck))

        composeRule.waitForIdle()

        composeRule.onNodeWithTag("tutorial_base").assertExists()
        composeRule.onNodeWithContentDescription("Add").assertExists()
        composeRule.onNodeWithTag("deck_item").assertExists()
    }



}


