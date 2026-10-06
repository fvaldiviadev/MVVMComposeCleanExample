package com.fvaldiviadev.domain.usecase

import com.fvaldiviadev.domain.model.CharacterRickMorty
import com.fvaldiviadev.domain.repository.CharacterRepository

class GetCharacterDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterRickMorty {
        return repository.getCharacterById(id)
    }
}