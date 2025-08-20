package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import javax.inject.Inject


class RandomPhraseUseCase @Inject constructor(
  private val repository: PhraseRepository
) {
    suspend operator fun invoke(): Phrase? {
        val phrasesToNotify = repository.getPhrasesToNotify()
        return when {
            phrasesToNotify.isNotEmpty() -> {
                val phrase = phrasesToNotify.random()
                repository.updateIsNotifiedById(phrase.id)
                phrase
            }

            repository.getAllPhrases().isNotEmpty() -> {
                // Reset all to not-notified
                repository.resetAllPhrasesToNotify()

                val phrase = repository.getAllPhrases().random()
                repository.updateIsNotifiedById(phrase.id)
                phrase
            }
            else -> null
        }
    }}