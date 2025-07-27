package com.example.starwars.koin

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.networking.requests.Service
import retrofit2.Response

class StarWarsRepositoryImpl(
    private val service: Service
): StarWarsRepository {
    override suspend fun getAllCharacters(): Response<List<CharactersItem>> {
        return service.getAllCharacters()
    }

    override suspend fun getAllPlanets(): Response<List<PlanetsItem>> {
        return service.getAllPlanets()
    }

    override suspend fun getAllStarships(): Response<List<ShipsItem>> {
        return service.getAllStarships()
    }
}