package com.fvaldiviadev.domain.usecase

import com.fvaldiviadev.domain.model.CharacterRickMorty
import com.fvaldiviadev.domain.repository.CharacterRepository

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): List<CharacterRickMorty> {
        return repository.getCharacters()
    }
}