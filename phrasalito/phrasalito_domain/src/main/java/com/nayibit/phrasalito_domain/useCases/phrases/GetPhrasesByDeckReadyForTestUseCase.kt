package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPhrasesByDeckReadyForTestUseCase @Inject constructor
    (private val repository: PhraseRepository)
{

    suspend operator fun invoke(idDeck: Int): Flow<Resource<List<Phrase>>> {
        return repository.getAllPhrasesByDeckId(idDeck)
            .map { result ->
                when (result) {
                    is Resource.Error -> Resource.Error(result.message)
                    Resource.Loading -> Resource.Loading
                    is Resource.Success -> {
                        val filtered = result.data.filter {
                            (it.translation != null && it.example != null) &&
                                    (it.translation.isNotEmpty() && it.example.isNotEmpty())
                        }
                        Resource.Success(filtered)
                    }
                }
            }
    }

}