package com.example.starwars.koin

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import retrofit2.Response

interface StarWarsRepository {
    suspend fun getAllCharacters(): Response<List<CharactersItem>>
    suspend fun getAllPlanets(): Response<List<PlanetsItem>>
    suspend fun getAllStarships(): Response<List<ShipsItem>>
    suspend fun getCharacterById(id: Int): Response<CharactersItem>
    suspend fun getPlanetById(id: Int): Response<PlanetsItem>
    suspend fun getStarshipById(id: Int): Response<ShipsItem>
}