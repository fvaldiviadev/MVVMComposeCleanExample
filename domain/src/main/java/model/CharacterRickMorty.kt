package model

data class CharacterRickMorty(
    val id: Int,
    val name: String,
    val image: String,
    val species: String,
    val episode: List<String>
)