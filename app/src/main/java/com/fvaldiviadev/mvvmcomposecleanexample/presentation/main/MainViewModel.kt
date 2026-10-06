package com.fvaldiviadev.mvvmcomposecleanexample.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.fvaldiviadev.domain.usecase.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    // _uiState es privado y mutable, solo el ViewModel puede modificarlo
    private val _uiState = MutableStateFlow(MainUiState())
    // uiState es público y de solo lectura, la UI solo puede observarlo
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadCharacters()
    }

    private fun loadCharacters() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            try {
                // Llamamos al caso de uso
                val charactersList = getCharactersUseCase()
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    characters = charactersList
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "Error desconocido"
                )
            }
        }
    }
}