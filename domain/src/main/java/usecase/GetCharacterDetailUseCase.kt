package usecase

import CharacterRepository
import model.CharacterRickMorty

class GetCharacterDetailUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterRickMorty {
        return repository.getCharacterById(id)
    }
}