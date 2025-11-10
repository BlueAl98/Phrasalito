package com.nayibit.phrasalito_domain.useCases.start

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.repository.DeckRepository
import javax.inject.Inject

class InsertFirstDeckUseCase @Inject constructor(
    private val repository: DeckRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        return repository.createInitialDeck()
    }
}