package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsertPhraseUseCase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend operator fun invoke(item: Phrase): Flow<Resource<Boolean>> {
        return repository.insert(item)
    }
}