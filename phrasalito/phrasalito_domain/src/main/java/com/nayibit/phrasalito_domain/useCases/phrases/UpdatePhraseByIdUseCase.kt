package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import com.nayibit.phrasalito_domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

import javax.inject.Inject

class UpdatePhraseByIdUseCase @Inject
 constructor(private val repository: PhraseRepository)  {

     suspend operator fun invoke(phrase: Phrase?): Flow<Resource<Boolean>> {
         val phraseById = phrase?.let { repository.getById(it.id) }
         return if (phraseById != null ) {
             repository.update( phraseById.copy(targetLanguage = phrase.targetLanguage, translation = phrase.translation))
         }else{
             flowOf(Resource.Error("Phrase not found"))
         }

     }

}