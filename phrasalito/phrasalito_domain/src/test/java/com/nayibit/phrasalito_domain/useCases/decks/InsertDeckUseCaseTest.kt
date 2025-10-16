package com.nayibit.phrasalito_domain.useCases.decks

import app.cash.turbine.test
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.repository.DeckRepository
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class InsertDeckUseCaseTest {

    private lateinit var insertDeckUseCase: InsertDeckUseCase

    val deckRepository : DeckRepository = mock()

    @Before
    fun setUp() {
        insertDeckUseCase = InsertDeckUseCase(deckRepository)
    }


    @Test
    fun `insert deck successfully`()  = runBlocking {

        val deck = Deck(name = "Test", maxCards = 10, lngCode = "en", languageName = "English")

        `when`(deckRepository.insert(deck)).thenReturn(flowOf(Resource.Success(deck)))

        insertDeckUseCase(deck).test{
            val result = awaitItem()
            assertTrue(result is Resource.Success)
            assertEquals(deck, (result as Resource.Success).data)
            awaitComplete()
        }
    }
  @Test
  fun `insert deck failure`()  = runBlocking {
      val deck = Deck(name = "Test", maxCards = 10, lngCode = "en", languageName = "English")
      val errorExpected = "Error to insert"

      `when`(deckRepository.insert(deck)).thenReturn(flowOf(Resource.Error(errorExpected)))

      insertDeckUseCase(deck).test{
          val result = awaitItem()
          assertTrue(result is Resource.Error)
          assertEquals(errorExpected, (result as Resource.Error).message)
          awaitComplete()
      }
  }
}