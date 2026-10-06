package com.fvaldiviadev.data.repository

import com.fvaldiviadev.data.mapper.toDomain
import com.fvaldiviadev.data.remote.Api
import com.fvaldiviadev.domain.model.CharacterRickMorty
import com.fvaldiviadev.domain.repository.CharacterRepository

class CharacterRepositoryImpl(
    private val api: Api
) : CharacterRepository {

    private var cachedCharacters: List<CharacterRickMorty> = emptyList()

    override suspend fun getCharacters(): List<CharacterRickMorty> {
        if (cachedCharacters.isEmpty()) {
            // Si la caché está vacía, llamamos a la API
            val response = api.getCharacters()
            // Mapeamos los DTOs a modelos de dominio y los guardamos
            cachedCharacters = response.results.map { it.toDomain() }
        }
        return cachedCharacters
    }

    override suspend fun getCharacterById(id: Int): CharacterRickMorty {
        return cachedCharacters.find { it.id == id }
            ?: throw Exception("Personaje no encontrado en caché")
    }
}