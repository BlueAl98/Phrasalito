package com.nayibit.phrasalito_domain.useCases.decks

import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.repository.DeckRepository
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertDeckUseCase  @Inject constructor(
    private val repository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck):Flow<Resource<Deck>> {
        return repository.insert(deck)
    }
}