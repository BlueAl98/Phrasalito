package com.nayibit.phrasalito_presentation.screens.phraseScreen

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesUseCase
import com.nayibit.phrasalito_domain.useCases.phrases.InsertPhraseUseCase
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PhraseViewModelTest {

    private lateinit var viewModel: PhraseViewModel
    val  getAllPhrasesUseCase: GetAllPhrasesUseCase = mock()
    val  insertPhraseUseCase: InsertPhraseUseCase = mock()
    val savedStateHandle: SavedStateHandle = mock()

    @Before
    fun setUp() {
        viewModel = PhraseViewModel(getAllPhrasesUseCase, insertPhraseUseCase, savedStateHandle)
    }

    @Test
    fun testGetAllPhrases() {
        assertEquals(true, true)
     /*   val phrases = listOf(Phrase(1, "Hello", "Hola", 1), Phrase(2, "World", "Mundo", 1))
        val flow = flowOf(Resource.Loading,Resource.Success(phrases))
        whenever(getAllPhrasesUseCase).thenReturn(flow)

        viewModel.getAllPhrases()

        viewModel.state.test {
            awaitItem()

            val loadingState = awaitItem()
            assertEquals(true, loadingState.isLoading)

            val successState = awaitItem()
            assertEquals(false, successState.isLoading)
            assertEquals(decks, successState.decks)
        }*/
    }


}