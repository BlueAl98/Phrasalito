package com.nayibit.feature_deckscreen.domain.useCases.decks

import com.nayibit.feature_deckscreen.domain.model.Deck
import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Result
import javax.inject.Inject

class UpdateDeckUseCase@Inject constructor(private val repository: DeckRepository) {
    suspend operator fun invoke(deck: Deck): Result<Unit, DatabaseError> {
        return repository.updateDeck(deck)
    }
}