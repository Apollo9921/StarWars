package com.example.starwars.koin

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.networking.requests.Service
import retrofit2.Response

/**
 * An implementation of the [StarWarsRepository] interface that retrieves data
 * from a network service.
 *
 * This class uses a [Service] instance (typically a Retrofit service) to make
 * HTTP requests to the Star Wars API.
 *
 * @property service The network service used to fetch Star Wars data.
 */
class StarWarsRepositoryImpl(
    private val service: Service
) : StarWarsRepository {

    /**
     * Retrieves a list of all Star Wars characters from the network service.
     *
     * @return A Retrofit [Response] containing a list of [CharactersItem] on success.
     */
    override suspend fun getAllCharacters(): Response<List<CharactersItem>> {
        return service.getAllCharacters()
    }

    /**
     * Retrieves a list of all Star Wars planets from the network service.
     *
     * @return A Retrofit [Response] containing a list of [PlanetsItem] on success.
     */
    override suspend fun getAllPlanets(): Response<List<PlanetsItem>> {
        return service.getAllPlanets()
    }

    /**
     * Retrieves a list of all Star Wars starships from the network service.
     *
     * @return A Retrofit [Response] containing a list of [ShipsItem] on success.
     */
    override suspend fun getAllStarships(): Response<List<ShipsItem>> {
        return service.getAllStarships()
    }

    /**
     * Retrieves a specific Star Wars character by their unique ID from the network service.
     *
     * @param id The unique identifier of the character.
     * @return A Retrofit [Response] containing a [CharactersItem] on success.
     */
    override suspend fun getCharacterById(id: Int): Response<CharactersItem> {
        return service.getCharacterById(id)
    }

    /**
     * Retrieves a specific Star Wars planet by its unique ID from the network service.
     *
     * @param id The unique identifier of the planet.
     * @return A Retrofit [Response] containing a [PlanetsItem] on success.
     */
    override suspend fun getPlanetById(id: Int): Response<PlanetsItem> {
        return service.getPlanetById(id)
    }

    /**
     * Retrieves a specific Star Wars starship by its unique ID from the network service.
     *
     * @param id The unique identifier of the starship.
     * @return A Retrofit [Response] containing a [ShipsItem] on success.
     */
    override suspend fun getStarshipById(id: Int): Response<ShipsItem> {
        return service.getStarshipById(id)
    }
}