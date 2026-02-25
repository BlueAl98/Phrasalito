package com.nayibit.phrasalito.navigation

import kotlinx.serialization.Serializable

@Serializable
object Routes {

    @Serializable
    object StartScreen

    @Serializable
    object CategoryScreen

    @Serializable
    data class DeckScreen(val id:Int)

    @Serializable
    data class PhraseScreen(val idDeck:Int)
}