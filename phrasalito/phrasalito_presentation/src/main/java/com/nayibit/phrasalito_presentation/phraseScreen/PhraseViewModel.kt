package com.nayibit.phrasalito_presentation.phraseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.phrasalito_domain.repository.PhraseRepository
import com.nayibit.phrasalito_domain.useCases.phrases.getAllPhrasesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhraseViewModel
    @Inject constructor(
        private val useCase: getAllPhrasesUseCase)
    : ViewModel()  {

     init {
        viewModelScope.launch {
            println(useCase())
        }
     }


}