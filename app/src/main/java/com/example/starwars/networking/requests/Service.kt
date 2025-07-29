package com.example.starwars.networking.requests

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Defines the API endpoints for interacting with a Star Wars data service.
 * This interface is used by Retrofit to generate the network request implementations.
 */
interface Service {

    /**
     * Fetches a list of all characters from the "people" endpoint.
     *
     * @return A Retrofit [Response] containing a list of [CharactersItem] on success.
     */
    @GET("people")
    suspend fun getAllCharacters(): Response<List<CharactersItem>>

    /**
     * Fetches a list of all planets from the "planets" endpoint.
     *
     * @return A Retrofit [Response] containing a list of [PlanetsItem] on success.
     */
    @GET("planets")
    suspend fun getAllPlanets(): Response<List<PlanetsItem>>

    /**
     * Fetches a list of all starships from the "starships" endpoint.
     *
     * @return A Retrofit [Response] containing a list of [ShipsItem] on success.
     */
    @GET("starships")
    suspend fun getAllStarships(): Response<List<ShipsItem>>

    /**
     * Fetches a specific character by their ID from the "people/{id}" endpoint.
     *
     * @param id The unique identifier of the character to fetch.
     * @return A Retrofit [Response] containing a [CharactersItem] on success.
     */
    @GET("people/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): Response<CharactersItem>

    /**
     * Fetches a specific planet by its ID from the "planets/{id}" endpoint.
     *
     * @param id The unique identifier of the planet to fetch.
     * @return A Retrofit [Response] containing a [PlanetsItem] on success.
     */
    @GET("planets/{id}")
    suspend fun getPlanetById(@Path("id") id: Int): Response<PlanetsItem>

    /**
     * Fetches a specific starship by its ID from the "starships/{id}" endpoint.
     *
     * @param id The unique identifier of the starship to fetch.
     * @return A Retrofit [Response] containing a [ShipsItem] on success.
     */
    @GET("starships/{id}")
    suspend fun getStarshipById(@Path("id") id: Int): Response<ShipsItem>
}