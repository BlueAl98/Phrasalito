package com.nayibit.phrasalito_presentation.screens.exerciseScreen

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nayibit.phrasalito_presentation.utils.exercisePhrase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val idDeck = savedStateHandle.get<Int>("idDeck")

    private val _state = MutableStateFlow(ExerciseUiState())
    val state: StateFlow<ExerciseUiState> = _state.asStateFlow()

    private val _eventChannel = Channel<ExerciseUiEvent>()
    val eventFlow: Flow<ExerciseUiEvent> = _eventChannel.receiveAsFlow()


    init {
        viewModelScope.launch {
            val exam = exercisePhrase("world", "hello world how are you" )
           _state.value = _state.value.copy(
                title = exam
            )
            Log.d("ExerciseViewModel", _state.value.title)
        }
    }

    fun onEvent(event: ExerciseUiEvent) {
        when (event) {
            is ExerciseUiEvent.OnStartClicked -> {
                viewModelScope.launch {
                    _eventChannel.send(ExerciseUiEvent.ShowToast("Exercise Started!"))
                }
            }
            else -> Unit
        }
    }
}