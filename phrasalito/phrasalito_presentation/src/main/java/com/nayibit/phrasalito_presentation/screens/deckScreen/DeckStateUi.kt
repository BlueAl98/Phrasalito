package com.nayibit.phrasalito_presentation.screens.deckScreen

import com.nayibit.phrasalito_presentation.model.Language


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
    val currentDeck: DeckUI = DeckUI(name = "", maxCards = 0),
    val listLanguages : List<Language> = listOf(
        Language(language = "English", alias = "en"),
        Language(language = "Spanish", alias = "es"),
        Language(language = "French", alias = "fr"),
        Language(language = "German", alias = "de"),
        Language(language = "Italian", alias = "it"),
        Language(language = "Portuguese", alias = "pt"),
        Language(language = "Russian", alias = "ru"),
        Language(language = "Japanese", alias = "ja"),

    ),
    val selectedLanguage: Language? = listLanguages.first()
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
