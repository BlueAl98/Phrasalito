package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class getAllPhrasesUseCase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend operator fun invoke(): Flow<List<Phrase>> {
       return repository.getAll()
    }
}