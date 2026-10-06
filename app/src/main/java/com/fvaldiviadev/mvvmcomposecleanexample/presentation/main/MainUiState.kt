package com.fvaldiviadev.mvvmcomposecleanexample.presentation.main

import model.CharacterRickMorty

data class MainUiState(
    val isLoading: Boolean = false,
    val characters: List<CharacterRickMorty> = emptyList(),
    val error: String? = null
)