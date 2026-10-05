package usecase

import CharacterRepository
import model.CharacterRickMorty

class GetCharactersUseCase(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(): List<CharacterRickMorty> {
        return repository.getCharacters()
    }
}