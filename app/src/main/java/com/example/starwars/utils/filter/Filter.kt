package com.example.starwars.utils.filter

import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.viewModel.SearchViewModel

object Filter {
    fun filterCharacters(
        species: List<String>?,
        gender: List<String>?,
        viewModel: SearchViewModel
    ): List<CharactersItem>? {
        var filteredList = viewModel.allCharacters ?: return null

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