package com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail

import model.CharacterRickMorty

data class DetailUiState(
    val character: CharacterRickMorty? = null,
    val error: String? = null
)