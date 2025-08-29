package com.nayibit.phrasalito_domain.useCases.phrases

import com.nayibit.common.util.Resource
import com.nayibit.phrasalito_domain.model.Phrase
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class UpdatePhraseByIdUseCase @Inject
 constructor(private val repository: PhraseRepository)  {

     suspend operator fun invoke(phrase: Phrase?): Flow<Resource<Boolean>> {
         val phraseById = phrase?.let { repository.getById(it.id) }
         return if (phraseById != null ) {
             repository.update(phraseById.copy(targetLanguage = phrase.targetLanguage, translation = phrase.translation, example = phrase.example))
         }else{
             flowOf(Resource.Error("Phrase not found"))
         }

     }

}