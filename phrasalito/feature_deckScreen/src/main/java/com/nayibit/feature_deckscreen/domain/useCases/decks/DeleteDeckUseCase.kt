package com.nayibit.feature_deckscreen.domain.useCases.decks

import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.utils.helpers.DatabaseError
import com.nayibit.utils.helpers.Resource
import com.nayibit.utils.helpers.Result
import javax.inject.Inject

class DeleteDeckUseCase @Inject constructor(private val repository: DeckRepository) {
    suspend operator fun invoke(id: Int): Result<Unit, DatabaseError> {
       return repository.deleteDeck(id)
    }

}