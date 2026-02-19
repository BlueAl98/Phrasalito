package com.nayibit.feature_deckscreen.domain.useCases.decks

import com.nayibit.feature_deckscreen.domain.repositories.DeckRepository
import com.nayibit.utils.helpers.Resource
import javax.inject.Inject

class DeleteDeckUseCase @Inject constructor(private val repository: DeckRepository) {
    suspend operator fun invoke(id: Int): Resource<Unit> {
       return repository.deleteDeck(id)
    }

}