package com.nayibit.phrasalito_presentation.deckScreen

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.useCases.decks.InsertDeckUseCase
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import app.cash.turbine.test
import com.nayibit.common.util.Resource
import com.nayibit.common.util.UiText.DynamicString
import com.nayibit.phrasalito_domain.useCases.decks.DeleteDeckUseCase
import com.nayibit.phrasalito_domain.useCases.decks.GetAllDecksUseCase
import com.nayibit.phrasalito_domain.useCases.decks.UpdateDeckUseCase
import com.nayibit.phrasalito_domain.useCases.tts.GetAvailableLanguagesUseCase
import com.nayibit.phrasalito_domain.useCases.tts.IsTextSpeechReadyUseCase
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckUiEvent.ShowToast
import com.nayibit.phrasalito_presentation.screens.deckScreen.DeckViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class DeckViewModelTest {

    private lateinit var viewModel: DeckViewModel

    private val mockInsertDeckUseCase : InsertDeckUseCase = mock()

    private val mockGetAllDecksUseCase : GetAllDecksUseCase = mock()

    private val mockDeleteDeckUseCase: DeleteDeckUseCase = mock()
    private val mockUpdateDeckUseCase: UpdateDeckUseCase = mock()
    private val mockGetAvailableLanguagesUseCase: GetAvailableLanguagesUseCase  = mock()
    private val mockIsTextSpeechReadyUseCase:  IsTextSpeechReadyUseCase = mock()

    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher) // Required for testing ViewModelScope

    runBlocking {
            whenever(mockGetAllDecksUseCase.invoke())
                .thenReturn(flowOf(Resource.Success(emptyList())))
        }
        viewModel = DeckViewModel(mockInsertDeckUseCase, mockGetAllDecksUseCase, mockDeleteDeckUseCase, mockUpdateDeckUseCase, mockGetAvailableLanguagesUseCase, mockIsTextSpeechReadyUseCase )

       // viewModel = DeckViewModel(mockInsertDeckUseCase, mockGetAllDecksUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

/*
    @Test
    fun `insertDeck should update state with success message`() = runTest{
        val deck = Deck(1, "Test Deck", 3, "en", "English")
        val flow = flowOf(Resource.Loading, Resource.Success(deck))
        whenever(mockInsertDeckUseCase(deck)).thenReturn(flow)

       viewModel.insertDeck(deck)


        viewModel.state.test {
            val initial = awaitItem()
            assertEquals(false, initial.isLoadingButton)

            val loadingState = awaitItem()
            println("Loading state: $loadingState")
            assertEquals(true, loadingState.isLoadingButton)

            val successState = awaitItem()
            println("Success state: $successState")
            assertEquals(false, successState.isLoadingButton)
            assertEquals(deck, successState.successInsertedDeck)
            assertNull(successState.errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
 }


    @Test
    fun `insertDeck emits success toast event`() = runTest {
        val deck = Deck(1, "Test", 3, "en", "English")
        val flow = flowOf(Resource.Loading, Resource.Success(deck))
        whenever(mockInsertDeckUseCase(deck)).thenReturn(flow)

        viewModel.insertDeck(deck)

        viewModel.eventFlow.test {
            val event = awaitItem()
            assertTrue(event is ShowToast)
            assertEquals(DynamicString("Deck inserted successfully"), (event as ShowToast).message)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `getAllDecks should update state with decks`() = runTest {
        val decks = listOf(Deck(1, "Deck 1", 5, "en", "English"), Deck(2, "Deck 2", 3, "es", "Spanish"))
        val flow = flowOf(Resource.Loading,Resource.Success(decks))
        whenever(mockGetAllDecksUseCase()).thenReturn(flow)

        viewModel.getAllDecks()

        viewModel.state.test {
            awaitItem()

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(decks, successState.decks)
        }

    }

    @Test
    fun `getAllDecks emits failure toast event`() = runTest {
        val errorMessage = "Error fetching decks"
        val flow = flowOf(Resource.Loading,Resource.Error(errorMessage))
        whenever(mockGetAllDecksUseCase()).thenReturn(flow)

        viewModel.getAllDecks()

        viewModel.state.test {
            awaitItem()

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val failureState = awaitItem()
            assertEquals(false, failureState.isLoading)
            assertEquals(errorMessage, failureState.errorMessage)
        }
    }
*/
}