package com.nayibit.phrasalito_data.repository

import app.cash.turbine.test
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_data.dao.DeckDao
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.mapper.toDeck
import com.nayibit.phrasalito_data.mapper.toDomain
import com.nayibit.phrasalito_data.mapper.toEntity
import com.nayibit.phrasalito_data.mapper.toPhrase
import com.nayibit.phrasalito_data.model.DeckWithPhrasesDto
import com.nayibit.phrasalito_data.utils.Constants.INITIAL_DECK
import com.nayibit.phrasalito_data.utils.Constants.INITIAL_PHRASES
import com.nayibit.phrasalito_domain.model.Deck
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class DeckRepositoryImplTest {

    private lateinit var repositoryImpl : DeckRepositoryImpl

    private val deckDao : DeckDao = mock()


    @Before
    fun setUp() {
        repositoryImpl = DeckRepositoryImpl(deckDao)
    }

    @Test
    fun `insert deck successfully`() = runBlocking  {

        //given
        val deckEntity = DeckEntity(name = "Test", lngCode = "en", languageName = "English")
        val deckDomain = deckEntity.toDeck()
        val expectReturn = 1L

        `when`(deckDao.insert(deckEntity)).thenReturn(expectReturn)


       repositoryImpl.insert(deckDomain).test {
           assertEquals(Resource.Loading, awaitItem())

           val result = awaitItem()

           assertTrue(result is Resource.Success)

           assertEquals(deckDomain, (result as Resource.Success).data)

           awaitComplete()
       }

    }


    @Test
    fun `insert deck failure`() = runBlocking  {

        //given
        val deckEntity = DeckEntity(name = "Test", lngCode = "en", languageName = "English")
        val deckDomain = deckEntity.toDeck()
        val errorExpected = "Error to insert"

        `when`(deckDao.insert(deckEntity)).thenThrow(RuntimeException(errorExpected))


        repositoryImpl.insert(deckDomain).test {
            assertEquals(Resource.Loading, awaitItem())

            val result = awaitItem()

            assertTrue(result is Resource.Error)
            assertEquals(errorExpected, (result as Resource.Error).message)
            awaitComplete()

        }

    }


    @Test
    fun `get all decks successfully`() = runBlocking {
        //given

        val fakeDecklist = listOf(
            DeckWithPhrasesDto(DeckEntity(id= 1, name = "Test1", lngCode = "en", languageName = "English"), emptyList()),
            DeckWithPhrasesDto(DeckEntity(id= 2, name = "Test2", lngCode = "es", languageName = "Spanish"), emptyList()),
        )
        val expectedFlow = flowOf(fakeDecklist)


        `when`(deckDao.getDecksWithPhrases()).thenReturn(expectedFlow)

        repositoryImpl.getAllDecks().test {
            assertEquals(Resource.Loading, awaitItem())

            val result = awaitItem()

            assertTrue(result is Resource.Success)
            assertEquals(fakeDecklist.map { it.toPhrase() }, (result as Resource.Success).data)
            awaitComplete()
        }

    }

    @Test
    fun `get all decks failure`() = runTest {
        // Given
        val errorExpected = "error to get decks"

        whenever(deckDao.getDecksWithPhrases()).thenReturn(
            flow { throw RuntimeException(errorExpected) }
        )

        // When / Then
        repositoryImpl.getAllDecks().test {
            assertEquals(Resource.Loading, awaitItem())

            val result = awaitItem()
            assertTrue(result is Resource.Error)
            assertEquals(errorExpected, (result as Resource.Error).message)

            awaitComplete()
        }
    }


    @Test
    fun `delete deck successfully`() = runBlocking{
        val deckFake =  DeckEntity(id= 1, name = "Test1", lngCode = "en", languageName = "English")
        `when`(deckDao.findDeckById(1)).thenReturn(deckFake)
        `when`(deckDao.deleteDeck(deckFake)).thenReturn(Unit)

        val result = repositoryImpl.deleteDeck(1)
        assertTrue(result is Resource.Success)
        verify(deckDao).findDeckById(1)
        verify(deckDao).deleteDeck(deckFake)
    }


    @Test
    fun `delete deck fails when dao throws exception`() = runTest {
        whenever(deckDao.findDeckById(1)).thenThrow(RuntimeException("DB error"))

        val result = repositoryImpl.deleteDeck(1)

        assertTrue(result is Resource.Error)
        assertEquals("DB error", (result as Resource.Error).message)
    }


    @Test
    fun `update deck successfully`() = runTest{
        val deckFake = Deck( name = "Test1", maxCards = 50, lngCode = "eng", languageName = "English")
        val deckToEntty = deckFake.toEntity()

        whenever(deckDao.updateDeck(deckToEntty)).thenReturn(Unit)

        val result = repositoryImpl.updateDeck(deckFake)

        assertTrue(result is Resource.Success)
        verify(deckDao, times(1)).updateDeck(deckToEntty)
    }

    @Test
    fun `update deck failure`() = runTest{
        val deckFake = Deck( name = "Test1", maxCards = 50, lngCode = "eng", languageName = "English")
        val deckToEntty = deckFake.toEntity()
        val errorexpected = "something is wrong"

        whenever(deckDao.updateDeck(deckToEntty)).thenThrow(RuntimeException(errorexpected))

        val result = repositoryImpl.updateDeck(deckFake)

        assertTrue(result is Resource.Error)
        assertEquals(errorexpected, (result as Resource.Error).message)
        verify(deckDao, times(1)).updateDeck(deckToEntty)
    }


    @Test
    fun `get all decks for notification successfully`() = runTest {
        //given

        val fakeDecklist = listOf(
            DeckWithPhrasesDto(DeckEntity(id= 1, name = "Test1", lngCode = "en", languageName = "English"), emptyList()),
            DeckWithPhrasesDto(DeckEntity(id= 2, name = "Test2", lngCode = "es", languageName = "Spanish"), emptyList()),
        )

       `when`(deckDao.getPhrasesForNotification()).thenReturn(fakeDecklist)

       val result =  repositoryImpl.getPhrasesForNotification()

        assertTrue(result is Resource.Success)
        assertEquals(fakeDecklist.map { it.toDomain()}, (result as Resource.Success).data)
        verify(deckDao ,times(1)).getPhrasesForNotification()

    }

    @Test
    fun `get all decks for notification failure`() = runTest {

        val errorExpected = "Error to get decks for notification"

        `when`(deckDao.getPhrasesForNotification()).thenThrow(RuntimeException(errorExpected))

        val result =  repositoryImpl.getPhrasesForNotification()

        assertTrue(result is Resource.Error)
        assertEquals(errorExpected, (result as Resource.Error).message)
        verify(deckDao ,times(1)).getPhrasesForNotification()

    }

    @Test
    fun `create initial deck successfully`() = runTest {

        whenever(deckDao.insertDeckWithPhrases(INITIAL_DECK, INITIAL_PHRASES)).thenReturn(Unit)

        val result = repositoryImpl.createInitialDeck()
        assertTrue(result is Resource.Success)
        assertEquals(true, (result as Resource.Success).data)
        verify(deckDao).deleteAll()
        verify(deckDao).insertDeckWithPhrases(INITIAL_DECK, INITIAL_PHRASES)
    }

    @Test
    fun `create initial deck failure`() = runTest {

        val errorExpected  = "Error to create initial deck"
        whenever(deckDao.insertDeckWithPhrases(INITIAL_DECK, INITIAL_PHRASES)).thenThrow(
            RuntimeException(errorExpected))

        val result = repositoryImpl.createInitialDeck()
        assertTrue(result is Resource.Error)
        assertEquals(errorExpected, (result as Resource.Error).message)
        verify(deckDao).deleteAll()
        verify(deckDao).insertDeckWithPhrases(INITIAL_DECK, INITIAL_PHRASES)
    }




}