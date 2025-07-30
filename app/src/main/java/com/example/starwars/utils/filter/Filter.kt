package com.example.starwars.utils.filter

import androidx.compose.runtime.snapshots.SnapshotStateList
import com.example.starwars.networking.model.characters.CharactersItem

object Filter {
    fun filterCharacters(
        species: List<String>?,
        gender: List<String>?,
        allCharactersSaved: SnapshotStateList<CharactersItem>?
    ): List<CharactersItem>? {
        var filteredList = allCharactersSaved ?: return null

        val hasSpeciesFilter = !species.isNullOrEmpty()
        val hasGenderFilter = !gender.isNullOrEmpty()

        val speciesLower = species?.map { it.lowercase() }
        val genderLower = gender?.map { it.lowercase() }

        return filteredList.filter { character ->
            val speciesMatch = if (hasSpeciesFilter && speciesLower != null) {
                character.species.any { characterSpecies ->
                    speciesLower.contains(characterSpecies.lowercase())
                }
            } else {
                true
            }

            val genderMatch = if (hasGenderFilter && genderLower != null) {
                genderLower.contains(character.gender.lowercase())
            } else {
                true
            }

            speciesMatch && genderMatch
        }
    }
}