package com.nayibit.phrasalito_domain.useCases.decks

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.repository.DeckRepository
import javax.inject.Inject

class UpdateDeckUseCase@Inject constructor(private val repository: DeckRepository) {
    suspend operator fun invoke(deck: Deck): Resource<Unit> {
        val deck = Deck(id = deck.id, name = deck.name, maxCards = deck.maxCards,
            lngCode = deck.lngCode, languageName = deck.languageName, isNotified = deck.isNotified)
        return repository.updateDeck(deck)
    }
}