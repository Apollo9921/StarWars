package com.example.starwars.koin

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.networking.model.species.SpeciesItem
import retrofit2.Response

/**
 * Defines the contract for accessing Star Wars data.
 * This interface abstracts the data source, allowing for different implementations
 * (e.g., network, local database, or mock data for testing).
 *
 * It's typically used by ViewModels or use cases to fetch data without needing to
 * know the specifics of how the data is retrieved.
 */
interface StarWarsRepository {

    /**
     * Retrieves a list of all Star Wars characters.
     *
     * @return A Retrofit [Response] containing a list of [CharactersItem] on success.
     */
    suspend fun getAllCharacters(): Response<List<CharactersItem>>

    /**
     * Retrieves a list of all Star Wars planets.
     *
     * @return A Retrofit [Response] containing a list of [PlanetsItem] on success.
     */
    suspend fun getAllPlanets(): Response<List<PlanetsItem>>

    /**
     * Retrieves a list of all Star Wars starships.
     *
     * @return A Retrofit [Response] containing a list of [ShipsItem] on success.
     */
    suspend fun getAllStarships(): Response<List<ShipsItem>>

    /**
     * Retrieves a specific Star Wars character by their unique ID.
     *
     * @param id The unique identifier of the character.
     * @return A Retrofit [Response] containing a [CharactersItem] on success.
     */
    suspend fun getCharacterById(id: Int): Response<CharactersItem>

    /**
     * Retrieves a specific Star Wars planet by its unique ID.
     *
     * @param id The unique identifier of the planet.
     * @return A Retrofit [Response] containing a [PlanetsItem] on success.
     */
    suspend fun getPlanetById(id: Int): Response<PlanetsItem>

    /**
     * Retrieves a specific Star Wars starship by its unique ID.
     *
     * @param id The unique identifier of the starship.
     * @return A Retrofit [Response] containing a [ShipsItem] on success.
     */
    suspend fun getStarshipById(id: Int): Response<ShipsItem>

    suspend fun getAllSpecies(): Response<List<SpeciesItem>>
}