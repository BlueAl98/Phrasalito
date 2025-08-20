package com.nayibit.phrasalito_domain.useCases.decks

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Deck
import com.nayibit.phrasalito_domain.repository.DeckRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertDeckUseCase  @Inject constructor(
    private val repository: DeckRepository
) {
    suspend operator fun invoke(deck: Deck):Flow<Resource<Deck>> {
        return repository.insert(deck)
    }
}