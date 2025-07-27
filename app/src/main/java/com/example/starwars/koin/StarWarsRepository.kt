package com.example.starwars.koin

import com.example.starwars.networking.model.characters.Characters
import com.example.starwars.networking.model.planets.Planets
import com.example.starwars.networking.model.ships.Ships
import retrofit2.Response

interface StarWarsRepository {
    suspend fun getAllCharacters(): Response<Characters>
    suspend fun getAllPlanets(): Response<Planets>
    suspend fun getAllStarships(): Response<Ships>
}