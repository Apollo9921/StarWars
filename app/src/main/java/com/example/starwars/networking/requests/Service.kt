package com.example.starwars.networking.requests

import com.example.starwars.networking.model.characters.Characters
import com.example.starwars.networking.model.planets.Planets
import com.example.starwars.networking.model.ships.Ships
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("people")
    suspend fun getAllCharacters(): Response<Characters>

    @GET("planets")
    suspend fun getAllPlanets(): Response<Planets>

    @GET("starships")
    suspend fun getAllStarships(): Response<Ships>
}