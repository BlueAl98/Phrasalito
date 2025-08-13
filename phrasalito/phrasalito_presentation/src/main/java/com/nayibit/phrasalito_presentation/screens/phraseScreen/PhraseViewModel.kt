package com.nayibit.phrasalito_presentation.screens.phraseScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.phrasalito_domain.useCases.phrases.GetAllPhrasesUseCase
import com.nayibit.phrasalito_domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhraseViewModel
    @Inject constructor(
        private val getAllPhrasesUseCase: GetAllPhrasesUseCase)
    : ViewModel()  {

    private val _state = MutableStateFlow(PhraseState())
    val state: StateFlow<PhraseState> = _state.asStateFlow()


     init {
        getAllPhrases()
     }

    private fun getAllPhrases() {
        viewModelScope.launch {
            getAllPhrasesUseCase()
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> _state.update { it.copy(isLoading = true) }
                        is Resource.Success -> _state.update {
                            it.copy(
                                isLoading = false,
                                phrases = result.data
                            )
                        }
                        is Resource.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            //   _event.send(UiEvent.ShowError(result.message))
                        }
                    }
                }
        }
    }


}