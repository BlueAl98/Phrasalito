package com.nayibit.phrasalito_domain.useCases.decks

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.DeckRepository
import javax.inject.Inject

class DeleteDeckUseCase @Inject constructor(private val repository: DeckRepository) {
    suspend operator fun invoke(id: Int): Resource<Unit> {
       return repository.deleteDeck(id)
    }

}