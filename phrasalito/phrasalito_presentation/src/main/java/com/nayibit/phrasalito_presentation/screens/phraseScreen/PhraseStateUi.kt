package com.nayibit.phrasalito_presentation.screens.phraseScreen

import com.nayibit.phrasalito_domain.model.Deck

data class PhraseStateUi(
    val isLoading: Boolean = false,
    val phrases: List<PhraseUi> = emptyList(),
    val isLoadingButton: Boolean = false,
    val showModal: Boolean = false,
    val successInsertedPhrase: Deck? = null,
    val firstPhrase: String = "",
    val translation: String = "")

data class PhraseUi(
    val id: Int,
    val targetLanguage: String,
    val translation: String,
    val isOptionsRevealed: Boolean = false
)