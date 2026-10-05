package usecase

import CharacterRepository

class GetCharacterDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): Character {
        return repository.getCharacterById(id)
    }
}