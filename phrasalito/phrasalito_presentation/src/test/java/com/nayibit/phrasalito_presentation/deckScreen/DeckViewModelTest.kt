package com.nayibit.phrasalito_presentation.deckScreen

import app.cash.turbine.test
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.useCases.dataStore.InsertTutorialDeckUseCase
import com.nayibit.phrasalito_domain.useCases.dataStore.IsTutorialDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.DeleteDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.GetAllDecksUseCase
import com.nayibit.phrasalito_domain.useCases.decks.InsertDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.UpdateDeckUseCase
import com.nayibit.phrasalito_domain.useCases.tts.GetAvailableLanguagesUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsTextSpeechReadyUseCase
import com.nayibit.phrasalito_presentation.mappers.toDeckUI
import com.nayibit.phrasalito_presentation.mappers.toLanguage
import com.nayibit.phrasalito_presentation.model.Language
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.ShowToast
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Locale

@ExperimentalCoroutinesApi
class DeckViewModelTest {

    private lateinit var viewModel: DeckViewModel

    private val mockInsertDeckUseCase : InsertDeckUseCase = mock()

    private val mockGetAllDecksUseCase : GetAllDecksUseCase = mock()
    private val mockDeleteDeckUseCase: DeleteDeckUseCase = mock()
    private val mockUpdateDeckUseCase: UpdateDeckUseCase = mock()
    private val mockGetAvailableLanguagesUseCase: GetAvailableLanguagesUseCase  = mock()
    private val mockIsTextSpeechReadyUseCase:  IsTextSpeechReadyUseCase = mock()
    private val mockIsTutorialDeckUseCase : IsTutorialDeckUseCase = mock()
    private val mockInsertTutorialDeckUseCase : InsertTutorialDeckUseCase = mock()

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Required for testing ViewModelScope

    runBlocking {
            whenever(mockGetAllDecksUseCase.invoke())
                .thenReturn(flowOf(Resource.Success(emptyList())))


        // 🩹 Add this mock so getAvailableLanguages() doesn’t crash
        whenever(mockIsTextSpeechReadyUseCase.invoke())
            .thenReturn(flowOf(Resource.Success(true)))

        whenever(mockGetAvailableLanguagesUseCase.invoke())
            .thenReturn(flowOf(Resource.Success(emptyList())))

        }

        whenever(mockIsTutorialDeckUseCase())
            .thenReturn(flowOf(Resource.Success(false)))

        viewModel = DeckViewModel(mockInsertDeckUseCase, mockGetAllDecksUseCase,
            mockDeleteDeckUseCase, mockUpdateDeckUseCase,
            mockGetAvailableLanguagesUseCase, mockIsTextSpeechReadyUseCase,
            mockIsTutorialDeckUseCase, mockInsertTutorialDeckUseCase)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    //GET ALL DECKS ISOLATION TEST
/*
    @Test
    fun `getAllDecks should update state with decks`() = runTest {
        val decks = listOf(Deck(1, "Deck 1", 5, "en", "English"))
        val flow = flowOf(Resource.Loading, Resource.Success(decks))

        whenever(mockGetAllDecksUseCase()).thenReturn(flow)

        // Act
        viewModel.getAllDecks()

        // Wait for coroutines
        advanceUntilIdle()

        // Now manually read the value of the state
        val currentState = viewModel.state.value

        // Assert
        assertEquals(decks.map { it.toDeckUI() }, currentState.decks)
        assertEquals(false, currentState.isLoading)
    }

    @Test
    fun `getAllDecks should emits filure toast event`()= runTest {
        val errorMessage = "Error to fetch decks"
        val flow = flowOf(Resource.Loading, Resource.Error(errorMessage))

        whenever(mockGetAllDecksUseCase()).thenReturn(flow)

        viewModel.getAllDecks()

        viewModel.state.test {
            awaitItem()
            val isLoading = awaitItem()
            assertEquals(true, isLoading.isLoading)
            val failureState = awaitItem()
            assertEquals(false, failureState.isLoading)
            assertEquals(errorMessage, failureState.errorMessage)
        }
    }


    // GET ALL DECKS FLOW WITH LANGUAGES ALL FLOW
    @Test
    fun `getAllDecks should update state with decks and available languages `() = runTest {
        // --- Arrange ---
        val decks = listOf(Deck(1, "Deck 1", 5, "en", "English"))
        val fakeSuccessLocales  = listOf(Locale("en"), Locale("es"), Locale("fr"))


        // Mock all flows
        whenever(mockGetAllDecksUseCase()).thenReturn(
            flowOf(Resource.Loading, Resource.Success(decks))
        )
        whenever(mockIsTextSpeechReadyUseCase()).thenReturn(
            flowOf(Resource.Success(true))
        )
        whenever(mockGetAvailableLanguagesUseCase()).thenReturn(
            flowOf(Resource.Loading,Resource.Success(fakeSuccessLocales))
        )

        // --- Act ---
        viewModel.getAllDecks()

        // --- Assert ---
        viewModel.state.test {
            val initial = awaitItem()
            assertFalse(initial.isLoading)

            val loadingDecks = awaitItem()
            assertTrue(loadingDecks.isLoading)

            val successDecks = awaitItem()
            assertEquals(decks.map { it.toDeckUI() }, successDecks.decks)

            // Wait for nested getAvailableLanguages() to complete
            val afterLanguages = awaitItem()
            assertEquals(false, afterLanguages.isLoading)
            assertEquals(
                listOf(
                    Language(
                        id = -1,
                        language = "Ninguno", // or whatever FIRST_ELEMENT_DROP_LANGUAGE_LIST is
                        alias = ""
                    )
                ) + fakeSuccessLocales.map { it.toLanguage() },
                afterLanguages.listLanguages
            )

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllDecks should not update state with decks and available languages failure`() = runTest {
        // --- Arrange ---
        val decks = listOf(Deck(1, "Deck 1", 5, "en", "English"))
        val errorMessage  =  "Error to fech languages"

        // Mock all flows
        whenever(mockGetAllDecksUseCase()).thenReturn(
            flowOf(Resource.Loading, Resource.Success(decks))
        )
        whenever(mockIsTextSpeechReadyUseCase()).thenReturn(
            flowOf(Resource.Success(true))
        )
        whenever(mockGetAvailableLanguagesUseCase()).thenReturn(
            flowOf(Resource.Loading,Resource.Error(errorMessage))
        )

        // --- Act ---
        viewModel.getAllDecks()

        // --- Assert ---
        viewModel.state.test {
            val initial = awaitItem()
            assertFalse(initial.isLoading)

            val loadingDecks = awaitItem()
            assertTrue(loadingDecks.isLoading)

            val successDecks = awaitItem()
            assertEquals(decks.map { it.toDeckUI() }, successDecks.decks)

            // Wait for nested getAvailableLanguages() to complete
            val afterLanguages = awaitItem()
            assertEquals(false, afterLanguages.isLoading)
            assertEquals(errorMessage, afterLanguages.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }


    // INSERT DECKS METHOD

    @Test
    fun `insertDeck should update state with success `() = runTest {
        val deck = Deck(1, "Test Deck", 3, "en", "English")

        val flow: Flow<Resource<Deck>> = flowOf(
            Resource.Loading,
            Resource.Success(deck)
        )

        // ✅ Explicitly mock the operator invoke
        whenever(mockInsertDeckUseCase.invoke(deck)).thenReturn(flow)

        // Act
        viewModel.insertDeck(deck)

        // Assert
        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(false, initial.isLoadingButton)

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoadingButton)

            val successState = awaitItem()
            assertEquals(false, successState.isLoadingButton)
            assertEquals(deck.toDeckUI(), successState.successInsertedDeck)
            assertNull(successState.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insertDeck failure result should be return message error`() = runTest {
        val messageError =  "Something bad happens"
        val fakeDeck = Deck(1, "Test Deck", 3, "en", "English")
        val flowResult = flowOf(Resource.Loading, Resource.Error(messageError))

        whenever(mockInsertDeckUseCase(fakeDeck)).thenReturn(flowResult)

        viewModel.insertDeck(fakeDeck)

        viewModel.state.test {
            awaitItem()
            val isLoading = awaitItem()
            assertEquals(true, isLoading.isLoadingButton)
            val failueState =  awaitItem()
            assertEquals(false, failueState.isLoadingButton)
            assertEquals(messageError, failueState.errorMessage)
        }

    }

    // UPDATE DECKS METHOD


    @Test
    fun `UpdateDeck should update state with success `() = runTest {
        val deck = Deck(1, "Test Deck", 3, "en", "English")


        // ✅ Explicitly mock the operator invoke
        whenever(mockUpdateDeckUseCase(deck)).thenReturn(Resource.Success(Unit))

        // Act
        viewModel.updateDeck(deck.toDeckUI())

        advanceUntilIdle()

        //assert
       assertEquals(false, viewModel.state.value.isLoadingButton)
       assertEquals(false, viewModel.state.value.showModal)

    }


*/


}