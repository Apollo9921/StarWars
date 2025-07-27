package com.example.starwars.utils.sort

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem

object Sorting {

    fun sortCharactersByNameAscendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedBy { it.name }
        }
        return null
    }

    fun sortCharactersByNameDescendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedByDescending { it.name }
        }
        return null
    }

    fun sortPlanetsByNameAscendant(allPlanets: List<PlanetsItem>?): List<PlanetsItem>? {
        allPlanets?.let { planets ->
            return planets.sortedBy { it.name }
        }
        return null
    }

    fun sortPlanetsByNameDescendant(allPlanets: List<PlanetsItem>?): List<PlanetsItem>? {
        allPlanets?.let { planets ->
            return planets.sortedByDescending { it.name }
        }
        return null
    }

    fun sortVehiclesByNameAscendant(allVehicles: List<ShipsItem>?): List<ShipsItem>? {
        allVehicles?.let { vehicles ->
            return vehicles.sortedBy { it.name }
        }
        return null
    }

    fun sortVehiclesByNameDescendant(allVehicles: List<ShipsItem>?): List<ShipsItem>? {
        allVehicles?.let { vehicles ->
            return vehicles.sortedByDescending { it.name }
        }
        return null
    }
}