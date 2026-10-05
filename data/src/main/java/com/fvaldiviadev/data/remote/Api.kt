package com.fvaldiviadev.data.remote

import com.fvaldiviadev.data.model.CharacterResponse
import retrofit2.http.GET

interface Api {
    @GET("https://rickandmortyapi.com/api/character")
    suspend fun getCharacters(): CharacterResponse
}