package com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import usecase.GetCharacterDetailUseCase

class DetailViewModel(
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase,
    private val characterId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    init {
        loadCharacter()
    }

    private fun loadCharacter() {
        viewModelScope.launch {
            try {
                val result = getCharacterDetailUseCase(characterId)
                _uiState.value = DetailUiState(character = result)
            } catch (e: Exception) {
                _uiState.value = DetailUiState(error = e.message)
            }
        }
    }
}