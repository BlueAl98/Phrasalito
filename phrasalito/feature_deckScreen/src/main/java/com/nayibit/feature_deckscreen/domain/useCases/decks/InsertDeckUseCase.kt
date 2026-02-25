package com.nayibit.feature_deckscreen.domain.useCases.decks

import com.nayibit.feature_deckscreen.domain.model.Deck
import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Resource
import com.nayibit.utils.helpers.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertDeckUseCase  @Inject constructor(
    private val repository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck, idCategory: Int): Result<Deck , DatabaseError> {
        return repository.insert(deck.copy(idCategory= idCategory))
    }
}