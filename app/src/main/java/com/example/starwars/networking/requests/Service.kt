package com.example.starwars.networking.requests

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import retrofit2.Response
import retrofit2.http.GET

interface Service {
    @GET("people")
    suspend fun getAllCharacters(): Response<List<CharactersItem>>

    @GET("planets")
    suspend fun getAllPlanets(): Response<List<PlanetsItem>>

    @GET("starships")
    suspend fun getAllStarships(): Response<List<ShipsItem>>
}