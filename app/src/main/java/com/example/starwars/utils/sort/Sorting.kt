package com.example.starwars.utils.sort

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.screens.allCharacters
import com.example.starwars.screens.allPlanets
import com.example.starwars.screens.allShips

/**
 * Utility object for sorting lists of Star Wars entities like characters, planets, and ships.
 *
 * This object provides functions to sort these entities by various attributes (e.g., name, birth year)
 * in both ascending and descending order. It also includes helper functions to apply these sorting
 * operations based on user selections, potentially from UI elements.
 *
 * Note: This class appears to modify global lists (`allCharacters`, `allPlanets`, `allShips`) directly.
 * Consider if this is the desired approach or if returning new sorted lists and managing state elsewhere
 * would be more robust.
 */
object Sorting {

    /**
     * Sorts a list of [CharactersItem] by name in ascending order.
     * @param allCharacters The list of characters to sort.
     * @return A new list sorted by name in ascending order, or null if the input list is null.
     */
    fun sortCharactersByNameAscendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedBy { it.name }
        }
        return null
    }

    /**
     * Sorts a list of [CharactersItem] by name in descending order.
     * @param allCharacters The list of characters to sort.
     * @return A new list sorted by name in descending order, or null if the input list is null.
     */
    fun sortCharactersByNameDescendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedByDescending { it.name }
        }
        return null
    }

    /**
     * Sorts a list of [PlanetsItem] by name in ascending order.
     * @param allPlanets The list of planets to sort.
     * @return A new list sorted by name in ascending order, or null if the input list is null.
     */
    fun sortPlanetsByNameAscendant(allPlanets: List<PlanetsItem>?): List<PlanetsItem>? {
        allPlanets?.let { planets ->
            return planets.sortedBy { it.name }
        }
        return null
    }

    /**
     * Sorts a list of [PlanetsItem] by name in descending order.
     * @param allPlanets The list of planets to sort.
     * @return A new list sorted by name in descending order, or null if the input list is null.
     */
    fun sortPlanetsByNameDescendant(allPlanets: List<PlanetsItem>?): List<PlanetsItem>? {
        allPlanets?.let { planets ->
            return planets.sortedByDescending { it.name }
        }
        return null
    }

    /**
     * Sorts a list of [ShipsItem] (vehicles/starships) by name in ascending order.
     * @param allVehicles The list of vehicles/starships to sort.
     * @return A new list sorted by name in ascending order, or null if the input list is null.
     */
    fun sortVehiclesByNameAscendant(allVehicles: List<ShipsItem>?): List<ShipsItem>? {
        allVehicles?.let { vehicles ->
            return vehicles.sortedBy { it.name }
        }
        return null
    }

    /**
     * Sorts a list of [ShipsItem] (vehicles/starships) by name in descending order.
     * @param allVehicles The list of vehicles/starships to sort.
     * @return A new list sorted by name in descending order, or null if the input list is null.
     */
    fun sortVehiclesByNameDescendant(allVehicles: List<ShipsItem>?): List<ShipsItem>? {
        allVehicles?.let { vehicles ->
            return vehicles.sortedByDescending { it.name }
        }
        return null
    }

    /**
     * Sorts a list of [CharactersItem] by birth year in ascending order.
     * Note: Assumes `birth_year` can be meaningfully compared (e.g., "BBY", "ABY" might need custom logic if not already handled).
     * @param allCharacters The list of characters to sort.
     * @return A new list sorted by birth year in ascending order, or null if the input list is null.
     */
    fun sortCharactersByYearAscendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedBy { it.birth_year }
        }
        return null
    }

    /**
     * Sorts a list of [CharactersItem] by birth year in descending order.
     * Note: Assumes `birth_year` can be meaningfully compared.
     * @param allCharacters The list of characters to sort.
     * @return A new list sorted by birth year in descending order, or null if the input list is null.
     */
    fun sortCharactersByYearDescendant(allCharacters: List<CharactersItem>?): List<CharactersItem>? {
        allCharacters?.let { characters ->
            return characters.sortedByDescending { it.birth_year }
        }
        return null
    }

    /**
     * Sorts the global `allCharacters` list based on selected criteria (name/year and asc/desc)
     * typically used in a search or filter context.
     * @param sortOptionNameYearSelected 0 for name, 1 for year.
     * @param sortOptionSelected 0 for ascending, 1 for descending.
     */
    fun sortCharacterWhenSearch(sortOptionNameYearSelected: Int, sortOptionSelected: Int) {
        when {
            sortOptionNameYearSelected == 0 && sortOptionSelected == 0 -> sortCharactersNameAscendant()
            sortOptionNameYearSelected == 0 && sortOptionSelected == 1 -> sortCharactersNameDescendant()
            sortOptionNameYearSelected == 1 && sortOptionSelected == 0 -> sortCharactersYearAscendant()
            sortOptionNameYearSelected == 1 && sortOptionSelected == 1 -> sortCharactersYearDescendant()
        }
    }

    /**
     * Sorts the global `allPlanets` list by name based on selected order (asc/desc)
     * typically used in a search or filter context.
     * @param sortOptionNameYearSelected Currently seems to only support name (0).
     * @param sortOptionSelected 0 for ascending, 1 for descending.
     */
    fun sortPlanetsWhenSearch(sortOptionNameYearSelected: Int, sortOptionSelected: Int) {
        when {
            sortOptionNameYearSelected == 0 && sortOptionSelected == 0 -> sortPlanetsNameAscendant()
            sortOptionNameYearSelected == 0 && sortOptionSelected == 1 -> sortPlanetsNameDescendant()
        }
    }

    /**
     * Sorts the global `allShips` list by name based on selected order (asc/desc)
     * typically used in a search or filter context.
     * @param sortOptionNameYearSelected Currently seems to only support name (0).
     * @param sortOptionSelected 0 for ascending, 1 for descending.
     */
    fun sortShipsWhenSearch(sortOptionNameYearSelected: Int, sortOptionSelected: Int) {
        when {
            sortOptionNameYearSelected == 0 && sortOptionSelected == 0 -> sortShipsNameAscendant()
            sortOptionNameYearSelected == 0 && sortOptionSelected == 1 -> sortShipsNameDescendant()
        }
    }

    /**
     * Sorts the appropriate global list (characters, planets, or ships) by name.
     * @param optionSelected A string indicating the type of entity to sort (e.g., "character", "planet", "ship").
     * @param character The string identifier for characters.
     * @param planet The string identifier for planets.
     * @param ship The string identifier for ships.
     * @param sortOptionSelected 0 for ascending, 1 for descending.
     */
    fun sortByName(optionSelected: String, character: String, planet: String, ship: String, sortOptionSelected: Int) {
        when {
            sortOptionSelected == 0 && optionSelected == character -> sortCharactersNameAscendant()
            sortOptionSelected == 1 && optionSelected == character -> sortCharactersNameDescendant()
            sortOptionSelected == 0 && optionSelected == planet -> sortPlanetsNameAscendant()
            sortOptionSelected == 1 && optionSelected == planet -> sortPlanetsNameDescendant()
            sortOptionSelected == 0 && optionSelected == ship -> sortShipsNameAscendant()
            sortOptionSelected == 1 && optionSelected == ship -> sortShipsNameDescendant()
        }
    }

    /**
     * Sorts the global `allCharacters` list by year.
     * @param sortOptionSelected 0 for ascending, 1 for descending.
     */
    fun sorByYear(sortOptionSelected: Int) { // Typo: should be sortByYear
        when (sortOptionSelected) {
            0 -> sortCharactersYearAscendant()
            1 -> sortCharactersYearDescendant()
        }
    }

    /**
     * Sorts the appropriate global list in ascending order by the selected attribute (name or year).
     * @param optionSelected A string indicating the type of entity (e.g., "character", "planet", "ship").
     * @param character The string identifier for characters.
     * @param planet The string identifier for planets.
     * @param ship The string identifier for ships.
     * @param sortOptionNameYearSelected 0 for name, 1 for year (year only applicable to characters).
     */
    fun sortAscendant(optionSelected: String, character: String, planet: String, ship: String, sortOptionNameYearSelected: Int) {
        when (sortOptionNameYearSelected) {
            0 -> { // Sort by name
                when (optionSelected) {
                    character -> sortCharactersNameAscendant()
                    planet -> sortPlanetsNameAscendant()
                    ship -> sortShipsNameAscendant()
                }
            }
            1 -> { // Sort by year
                if (optionSelected == character) {
                    sortCharactersYearAscendant()
                }
                // Else: No action for planets or ships by year
            }
        }
    }

    /**
     * Sorts the appropriate global list in descending order by the selected attribute (name or year).
     * @param optionSelected A string indicating the type of entity (e.g., "character", "planet", "ship").
     * @param character The string identifier for characters.
     * @param planet The string identifier for planets.
     * @param ship The string identifier for ships.
     * @param sortOptionNameYearSelected 0 for name, 1 for year (year only applicable to characters).
     */
    fun sortDescendant(optionSelected: String, character: String, planet: String, ship: String, sortOptionNameYearSelected: Int) {
        when (sortOptionNameYearSelected) {
            0 -> { // Sort by name
                when (optionSelected) {
                    character -> sortCharactersNameDescendant()
                    planet -> sortPlanetsNameDescendant()
                    ship -> sortShipsNameDescendant()
                }
            }
            1 -> { // Sort by year
                if (optionSelected == character) {
                    sortCharactersYearDescendant()
                }
                // Else: No action for planets or ships by year
            }
        }
    }

    /**
     * Sorts the global `allCharacters` list by name in ascending order by clearing and re-adding sorted items.
     */
    fun sortCharactersNameAscendant() {
        val newCharacters = sortCharactersByNameAscendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    /**
     * Sorts the global `allCharacters` list by name in descending order by clearing and re-adding sorted items.
     */
    fun sortCharactersNameDescendant() {
        val newCharacters = sortCharactersByNameDescendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    /**
     * Sorts the global `allCharacters` list by year in ascending order by clearing and re-adding sorted items.
     */
    fun sortCharactersYearAscendant() {
        val newCharacters = sortCharactersByYearAscendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    /**
     * Sorts the global `allCharacters` list by year in descending order by clearing and re-adding sorted items.
     */
    fun sortCharactersYearDescendant() {
        val newCharacters = sortCharactersByYearDescendant(allCharacters)
        allCharacters?.clear()
        newCharacters?.let {
            allCharacters?.addAll(it)
        }
    }

    /**
     * Sorts the global `allPlanets` list by name in ascending order by clearing and re-adding sorted items.
     */
    fun sortPlanetsNameAscendant() {
        val newPlanets = sortPlanetsByNameAscendant(allPlanets) // Changed variable name for clarity
        allPlanets?.clear()
        newPlanets?.let {
            allPlanets?.addAll(it)
        }
    }

    /**
     * Sorts the global `allPlanets` list by name in descending order by clearing and re-adding sorted items.
     */
    fun sortPlanetsNameDescendant() {
        val newPlanets = sortPlanetsByNameDescendant(allPlanets) // Changed variable name for clarity
        allPlanets?.clear()
        newPlanets?.let {
            allPlanets?.addAll(it)
        }
    }

    /**
     * Sorts the global `allShips` list by name in ascending order by clearing and re-adding sorted items.
     */
    fun sortShipsNameAscendant() {
        val newShips = sortVehiclesByNameAscendant(allShips) // Changed variable name for clarity
        allShips?.clear()
        newShips?.let {
            allShips?.addAll(it)
        }
    }

    /**
     * Sorts the global `allShips` list by name in descending order by clearing and re-adding sorted items.
     */
    fun sortShipsNameDescendant() {
        val newShips = sortVehiclesByNameDescendant(allShips) // Changed variable name for clarity
        allShips?.clear()
        newShips?.let {
            allShips?.addAll(it)
        }
    }
}