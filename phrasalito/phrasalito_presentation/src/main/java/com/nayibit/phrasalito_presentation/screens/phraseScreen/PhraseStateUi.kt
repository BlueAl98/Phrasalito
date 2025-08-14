package com.nayibit.phrasalito_presentation.screens.phraseScreen

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.model.Phrase

data class PhraseStateUi(
    val isLoading: Boolean = false,
    val phrases: List<Phrase> = emptyList(),
    val isLoadingButton: Boolean = false,
    val showModal: Boolean = false,
    val successInsertedPhrase: Deck? = null,
    val firstPhrase: String = "",
    val translation: String = ""

)
