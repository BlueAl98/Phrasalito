package com.ie.feature_startscreen.domain.usecases

import com.ie.feature_startscreen.domain.model.Phrase
import com.ie.feature_startscreen.domain.repositories.CategoryRepository
import javax.inject.Inject

class GetRandomPhraseForNotifyUseCase @Inject constructor(
    private val repository : CategoryRepository
)
{
 suspend operator fun invoke(): Phrase? {
        val phrasesToNotify = repository.getCategoriesByLanguage()
        phrasesToNotify.onSuccess { phrases ->
            return phrases.randomOrNull()
        }.onFailure{
            return null
        }
        return null
    }

}