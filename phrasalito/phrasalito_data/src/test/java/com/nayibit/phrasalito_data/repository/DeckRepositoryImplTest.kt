package com.nayibit.phrasalito_data.repository

import app.cash.turbine.test
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_data.dao.DeckDao
import com.nayibit.phrasalito_data.entities.DeckEntity
import com.nayibit.phrasalito_data.mapper.toDeck
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock

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


}