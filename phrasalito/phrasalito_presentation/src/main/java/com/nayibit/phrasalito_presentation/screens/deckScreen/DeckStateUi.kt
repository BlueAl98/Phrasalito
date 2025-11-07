package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.phrasalito_presentation.model.Language


data class DeckStateUi (
    val isLoading: Boolean = false,
    val successInsertedDeck: DeckUI? = null,
    val decks: List<DeckUI> = emptyList(),
    val errorMessage: String? = null,
    val showModal: Boolean = false,
    val isLoadingButton: Boolean = false,
    val bodyModal: BodyDeckModalEnum = BodyDeckModalEnum.BODY_INSERT_DECK,
    val currentDeck: DeckUI = DeckUI(name = "", numCards = 0),
    val listLanguages : List<Language> = emptyList(),
    val currentStep : Int = 0,
    val showTutorial : Boolean = true
)

data class DeckUI(
    val id: Int = 0,
    val name: String = "",
    val numCards: Int = 0,
    val isSwiped: Boolean = false,
    val isNotified: Boolean = false,
    val selectedLanguage: Language? = null,
    val maxCards: Int = 0
)

enum class BodyDeckModalEnum() {
    BODY_INSERT_DECK,
    BODY_UPDATE_DECK,
    BODY_DELETE_DECK
}
