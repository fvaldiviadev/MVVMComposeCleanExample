package com.fvaldiviadev.mvvmcomposecleanexample.presentation.detail

import com.fvaldiviadev.domain.model.CharacterRickMorty

data class DetailUiState(
    val character: CharacterRickMorty? = null,
    val error: String? = null
)