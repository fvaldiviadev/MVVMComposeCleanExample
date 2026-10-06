package com.fvaldiviadev.data.mapper

import com.fvaldiviadev.data.model.CharacterDto
import com.fvaldiviadev.domain.model.CharacterRickMorty

fun CharacterDto.toDomain(): CharacterRickMorty {
    return CharacterRickMorty(
        id = this.id,
        name = this.name,
        image = this.image,
        species = this.species,
        episode = this.episode
    )
}