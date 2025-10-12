package com.nayibit.phrasalito_presentation.screens.deckScreen


data class DeckStateUi (
    val isLoading: Boolean = false,
    val successInsertedDeck: DeckUI? = null,
    val decks: List<DeckUI> = emptyList(),
    val errorMessage: String? = null,
    val showModal: Boolean = false,
    val nameDeck: String = "",
    val maxDeck: Int = 0,
    val isLoadingButton: Boolean = false,
    val bodyModal: BodyDeckModalEnum = BodyDeckModalEnum.BODY_INSERT_DECK,
    val currentDeck: DeckUI = DeckUI(name = "", maxCards = 0)
)

data class DeckUI(
    val id: Int = 0,
    val name: String = "",
    val maxCards: Int = 0,
    val isSwiped: Boolean = false
)

enum class BodyDeckModalEnum() {
    BODY_INSERT_DECK,
    BODY_UPDATE_DECK,
    BODY_DELETE_DECK
}
