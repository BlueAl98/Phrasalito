package com.nayibit.phrasalito_domain.useCases.decks

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.repository.DeckRepository
import javax.inject.Inject

class UpdateDeckUseCase@Inject constructor(private val repository: DeckRepository) {
    suspend operator fun invoke(id: Int, name: String, lngCode: String, languageName: String): Resource<Unit> {
        val deck = Deck(id = id, name = name, maxCards = 10, lngCode = lngCode, languageName = languageName)
        return repository.updateDeck(deck)
    }
}