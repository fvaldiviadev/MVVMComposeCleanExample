package com.fvaldiviadev.data.model

data class CharacterDto(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val episode: List<String>
)