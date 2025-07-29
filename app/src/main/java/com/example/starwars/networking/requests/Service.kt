package com.example.starwars.networking.requests

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Service {
    @GET("people")
    suspend fun getAllCharacters(): Response<List<CharactersItem>>

    @GET("planets")
    suspend fun getAllPlanets(): Response<List<PlanetsItem>>

    @GET("starships")
    suspend fun getAllStarships(): Response<List<ShipsItem>>

    @GET("people/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CharactersItem>

    @GET("planets/{id}")
    suspend fun getPlanetById(@Path("id") id: Int): Response<PlanetsItem>

    @GET("starships/{id}")
    suspend fun getStarshipById(@Path("id") id: Int): Response<ShipsItem>
}