import model.CharacterRickMorty

interface CharacterRepository {
    suspend fun getCharacters(): List<CharacterRickMorty>
    suspend fun getCharacterById(id: Int): CharacterRickMorty
}