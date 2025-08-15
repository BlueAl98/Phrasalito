package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.phrasalito_domain.repository.PhraseRepository
import javax.inject.Inject


class RandomPhraseUseCase @Inject constructor(
    private val repository: PhraseRepository
) {
    suspend operator fun invoke() = repository.getRandomPhrase()
}