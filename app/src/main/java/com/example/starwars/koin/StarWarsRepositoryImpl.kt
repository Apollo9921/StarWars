package com.example.starwars.koin

import com.example.starwars.networking.model.characters.Characters
import com.example.starwars.networking.model.planets.Planets
import com.example.starwars.networking.model.ships.Ships
import com.example.starwars.networking.requests.Service
import retrofit2.Response

class StarWarsRepositoryImpl(
    private val service: Service
): StarWarsRepository {
    override suspend fun getAllCharacters(): Response<Characters> {
        return service.getAllCharacters()
    }

    override suspend fun getAllPlanets(): Response<Planets> {
        return service.getAllPlanets()
    }

    override suspend fun getAllStarships(): Response<Ships> {
        return service.getAllStarships()
    }
}