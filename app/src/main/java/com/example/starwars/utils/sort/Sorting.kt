package com.example.starwars.utils.sort

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.screens.allCharacters
import com.example.starwars.screens.allPlanets
import com.example.starwars.screens.allShips

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

    fun sortCharactersByYearAscendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedBy { it.birth_year }
        }
        return null
    }

    fun sortCharactersByYearDescendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedByDescending { it.birth_year }
        }
        return null
    }

    fun sortCharacterWhenSearch(sortOptionNameYearSelected: Int, sortOptionSelected: Int) {
        when {
            sortOptionNameYearSelected == 0 && sortOptionSelected == 0 -> {
                sortCharactersNameAscendant()
            }
            sortOptionNameYearSelected == 0 && sortOptionSelected == 1 -> {
                sortCharactersNameDescendant()
            }
            sortOptionNameYearSelected == 1 && sortOptionSelected == 0 -> {
                sortCharactersYearAscendant()
            }
            sortOptionNameYearSelected == 1 && sortOptionSelected == 1 -> {
                sortCharactersYearDescendant()
            }
        }
    }

    fun sortPlanetsWhenSearch(sortOptionNameYearSelected: Int, sortOptionSelected: Int) {
        when {
            sortOptionNameYearSelected == 0 && sortOptionSelected == 0 -> {
                sortPlanetsNameAscendant()
            }
            sortOptionNameYearSelected == 0 && sortOptionSelected == 1 -> {
                sortPlanetsNameDescendant()
            }
        }
    }

    fun sortShipsWhenSearch(sortOptionNameYearSelected: Int, sortOptionSelected: Int) {
        when {
            sortOptionNameYearSelected == 0 && sortOptionSelected == 0 -> {
                sortShipsNameAscendant()
            }
            sortOptionNameYearSelected == 0 && sortOptionSelected == 1 -> {
                sortShipsNameDescendant()
            }
        }
    }

    fun sortByName(optionSelected: String, character: String, planet: String, ship: String, sortOptionSelected: Int) {
        when {
            sortOptionSelected == 0 && optionSelected == character -> {
                sortCharactersNameAscendant()
            }
            sortOptionSelected == 1 && optionSelected == character -> {
                sortCharactersNameDescendant()
            }
            sortOptionSelected == 0 && optionSelected == planet -> {
                sortPlanetsNameAscendant()
            }
            sortOptionSelected == 1 && optionSelected == planet -> {
                sortPlanetsNameDescendant()
            }
            sortOptionSelected == 0 && optionSelected == ship -> {
                sortShipsNameAscendant()
            }
            sortOptionSelected == 1 && optionSelected == ship -> {
                sortShipsNameDescendant()
            }
        }
    }

    fun sorByYear(sortOptionSelected: Int) {
        when (sortOptionSelected) {
            0 -> {
                sortCharactersYearAscendant()
            }
            1 -> {
                sortCharactersYearDescendant()
            }
        }
    }

    fun sortAscendant(optionSelected: String, character: String, planet: String, ship: String, sortOptionNameYearSelected: Int) {
        when (sortOptionNameYearSelected) {
            0 -> {
                when (optionSelected) {
                    character -> {
                        sortCharactersNameAscendant()
                    }
                    planet -> {
                        sortPlanetsNameAscendant()
                    }
                    ship -> {
                        sortShipsNameAscendant()
                    }
                }
            }
            1 -> {
                if (optionSelected == character) {
                    sortCharactersYearAscendant()
                }
            }
        }
    }

    fun sortDescendant(optionSelected: String, character: String, planet: String, ship: String, sortOptionNameYearSelected: Int) {
        when (sortOptionNameYearSelected) {
            0 -> {
                when (optionSelected) {
                    character -> {
                        sortCharactersNameDescendant()
                    }
                    planet -> {
                        sortPlanetsNameDescendant()
                    }
                    ship -> {
                        sortShipsNameDescendant()
                    }
                }
            }
            1 -> {
                if (optionSelected == character) {
                    sortCharactersYearDescendant()
                }
            }
        }
    }

    fun sortCharactersNameAscendant() {
        val newCharacters = sortCharactersByNameAscendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    fun sortCharactersNameDescendant() {
        val newCharacters = sortCharactersByNameDescendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    fun sortCharactersYearAscendant() {
        val newCharacters = sortCharactersByYearAscendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    fun sortCharactersYearDescendant() {
        val newCharacters = sortCharactersByYearDescendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    fun sortPlanetsNameAscendant() {
        val newCharacters = sortPlanetsByNameAscendant(allPlanets)
        allPlanets?.clear()
        newCharacters?.let {
            allPlanets?.addAll(it)
        }
    }

    fun sortPlanetsNameDescendant() {
        val newCharacters = sortPlanetsByNameDescendant(allPlanets)
        allPlanets?.clear()
        newCharacters?.let {
            allPlanets?.addAll(it)
        }
    }

    fun sortShipsNameAscendant() {
        val newCharacters = sortVehiclesByNameAscendant(allShips)
        allShips?.clear()
        newCharacters?.let {
            allShips?.addAll(it)
        }
    }

    fun sortShipsNameDescendant() {
        val newCharacters = sortVehiclesByNameDescendant(allShips)
        allShips?.clear()
        newCharacters?.let {
            allShips?.addAll(it)
        }
    }
}