package com.nayibit.phrasalito_presentation.deckScreen

import com.nayibit.common.util.Constants.FIRST_ELEMENT_DROP_LANGUAGE_LIST
import com.nayibit.common.util.Resource
import com.nayibit.common.util.UiText.DynamicString
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
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.Locale
import kotlin.collections.plus

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


    @Test
    fun `getAllDecks should update state with decks and languages`() = runTest {
        val decks = listOf(Deck(1, "Deck 1", 5, "en", "English"))
        val flow = flowOf(Resource.Loading, Resource.Success(decks))
        val languages = Resource.Success(listOf(Locale("en", "usa"), Locale("es", "mexico"), Locale("fr", "france")))

        val isTextSpeechReady = Resource.Success(true)

        whenever(mockGetAllDecksUseCase()).thenReturn(flow)
        whenever(mockIsTextSpeechReadyUseCase()).thenReturn(flowOf(isTextSpeechReady))

        whenever(mockGetAvailableLanguagesUseCase()).thenReturn(
            flowOf(languages)
        )
        // Act
        viewModel.getAllDecks()


        // Wait for coroutines
        advanceUntilIdle()

        // Now manually read the value of the state
        val currentState = viewModel.state.value

        // Assert
        assertEquals(currentState.isLoading, false)
        assertEquals(decks.map { it.toDeckUI() }, currentState.decks)
        assertEquals(currentState.listLanguages, listOf(Language(id = -1, language = FIRST_ELEMENT_DROP_LANGUAGE_LIST, alias = "")) + languages.data.map { it.toLanguage() })


    }


    @Test
    fun `getAllDecks should return error`() = runTest {
        val errorExpect = "Error fetch decks"

        whenever(mockGetAllDecksUseCase()).thenReturn(
            flowOf(Resource.Loading, Resource.Error(errorExpect))
        )


        // Start collecting events BEFORE invoking action
        val eventDeferred = async {
            viewModel.eventFlow.first()
        }

        // Act
        viewModel.getAllDecks()
        advanceUntilIdle()

        val event = withTimeout(5_000) { eventDeferred.await() }

        val state = viewModel.state.value

        // Assert
        assertEquals(false, state.isLoading)
        assertEquals(errorExpect, state.errorMessage)
       assertEquals(ShowToast(DynamicString("Error: $errorExpect")), event)
    }


    @Test
    fun `insertDeck is successfully`() = runTest {
        val fakeDeck = Deck(1, "Deck 1", maxCards = 10, lngCode = "eng", languageName = "English")

        whenever(mockInsertDeckUseCase(fakeDeck)).thenReturn(flowOf(Resource.Success(fakeDeck)))


        // Start collecting events BEFORE invoking action
        val eventDeferred = async {
            viewModel.eventFlow.first()
        }
        viewModel.insertDeck(fakeDeck)
        advanceUntilIdle()

        val currentState = viewModel.state.value
        val event = withTimeout(5_000) { eventDeferred.await() }

        assertEquals(currentState.isLoading, false)
        assertEquals(currentState.successInsertedDeck, fakeDeck.toDeckUI())
        assertEquals(event, ShowToast(DynamicString("Deck inserted successfully")))
        assert(!currentState.showModal)

    }


    @Test
    fun `insertDeck is failure`() = runTest {
        val fakeDeck = Deck(1, "Deck 1", maxCards = 10, lngCode = "eng", languageName = "English")
        val errorExpected =  "Error expected"

        whenever(mockInsertDeckUseCase(fakeDeck)).thenReturn(flowOf(Resource.Error(errorExpected)))


        // Start collecting events BEFORE invoking action
        val eventDeferred = async {
            viewModel.eventFlow.first()
        }
        viewModel.insertDeck(fakeDeck)
        advanceUntilIdle()

        val currentState = viewModel.state.value
        val event = withTimeout(5_000) { eventDeferred.await() }

        assertEquals(currentState.isLoading, false)
        assertEquals(currentState.errorMessage, errorExpected)
        assertEquals(ShowToast(DynamicString("Error: $errorExpected")), event)
        assert(!currentState.showModal)

    }




}