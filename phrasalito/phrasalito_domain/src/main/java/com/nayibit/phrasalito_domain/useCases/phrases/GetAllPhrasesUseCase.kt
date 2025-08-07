package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllPhrasesUseCase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Phrase>>>  {
       return repository.getAll()
    }
}