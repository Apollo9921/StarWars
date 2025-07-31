package com.example.starwars.utils.filter

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.species.SpeciesItem

object Filter {
    fun filterCharacters(
        species: List<String>?,
        gender: List<String>?,
        allCharactersSaved: SnapshotStateList<CharactersItem>?,
        allSpecies: SnapshotStateList<SpeciesItem>?
    ): List<CharactersItem>? {
        val filteredList = allCharactersSaved ?: return null

        val hasSpeciesFilter = !species.isNullOrEmpty()
        val hasGenderFilter = !gender.isNullOrEmpty()

        val genderLower = gender?.map { it.lowercase() }

        // This part extracts the URLs of characters belonging to the selected species
        val characterUrlsFromSelectedSpecies: List<String>? = if (hasSpeciesFilter) {
            allSpecies?.mapNotNull { speciesItem ->
                if (species.contains(speciesItem.name) == true) {
                    speciesItem.people // List of character URLs for this species
                } else {
                    null
                }
            }?.flatten()?.distinct() // Flatten the list of lists and get unique URLs
        } else {
            null
        }


        return filteredList.filter { character ->
            val speciesMatch = if (hasSpeciesFilter && characterUrlsFromSelectedSpecies != null) {
                characterUrlsFromSelectedSpecies.contains(character.url)
            } else {
                true // No species filter applied or relevant species data is unavailable/empty
            }

            val genderMatch = if (hasGenderFilter && genderLower != null) {
                genderLower.contains(character.gender.lowercase())
            } else {
                true // No gender filter applied or gender list is empty
            }

            speciesMatch && genderMatch
        }
    }
}