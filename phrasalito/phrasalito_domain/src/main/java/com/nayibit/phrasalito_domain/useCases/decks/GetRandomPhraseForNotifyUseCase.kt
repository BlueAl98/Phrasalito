package com.nayibit.phrasalito_domain.useCases.decks
import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.DeckRepository
import javax.inject.Inject

class GetRandomPhraseForNotifyUseCase @Inject constructor(
    private val repository : DeckRepository)
{
    suspend operator fun invoke(): Phrase? {
        val phrasesToNotify = repository.getPhrasesForNotification()
       when (phrasesToNotify) {
          is Resource.Error -> {
              return null
          }
          is Resource.Success-> {
              val phrases = phrasesToNotify.data.map{
                  it?.phrases?.randomOrNull()
              }
              return phrases.randomOrNull()
              }

          else -> {}
      }
        return null
    }

}