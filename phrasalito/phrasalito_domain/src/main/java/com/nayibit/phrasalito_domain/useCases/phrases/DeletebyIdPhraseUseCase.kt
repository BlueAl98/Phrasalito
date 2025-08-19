package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.phrasalito_domain.repository.PhraseRepository
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class DeletebyIdPhraseUseCase @Inject
constructor(private val repository: PhraseRepository)
{
    suspend operator fun invoke(id: Int):Flow<Resource<Boolean>> {
        val phrase = repository.getById(id)
        return if (phrase != null) {
            repository.delete(phrase)
        } else {
            flowOf(Resource.Error("Phrase not found"))
        }
    }

}