package com.example.starwars.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.core.customFonts
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.utils.size.ScreenSizeUtils

/**
 * A Composable function that displays a list of Star Wars entities (characters, planets, or ships)
 * based on the [optionSelected]. Each item in the list is clickable and navigates to a
 * details screen.
 *
 * This function uses a [LazyColumn] for efficient display of potentially long lists.
 *
 * @param allCharacters A list of [CharactersItem] to display if "character" is selected. Can be null.
 * @param allPlanets A list of [PlanetsItem] to display if "planet" is selected. Can be null.
 * @param allVehicles A list of [ShipsItem] to display if "ship" is selected. Can be null.
 * @param optionSelected A string indicating the type of entity to display. This should match
 *                       the string resources for "characters", "planets", or "ships".
 * @param navController The [NavHostController] used for navigating to the details screen.
 */
@Composable
fun ListResults(
    allCharacters: List<CharactersItem>?,
    allPlanets: List<PlanetsItem>?,
    allVehicles: List<ShipsItem>?,
    optionSelected: String,
    navController: NavHostController
) {
    // Calculate dynamic text size for list items based on screen width.
    val resultTextSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 20).sp

    // Get string resources for comparison with optionSelected.
    // This makes the component more robust to string changes and localization.
    val character = stringResource(R.string.characters)
    val planet = stringResource(R.string.planets)
    val ship = stringResource(R.string.ships)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, bottom = 60.dp), // Apply padding around the list.
        horizontalAlignment = Alignment.CenterHorizontally // Center items horizontally within the LazyColumn.
    ) {
        when (optionSelected) {
            character -> {
                // Display list of characters.
                // The `items` builder takes the count of items. `allCharacters?.size ?: 0` handles nullability.
                items(allCharacters?.size ?: 0) { index ->
                    // Safely access item from the list.
                    val characterItem = allCharacters?.getOrNull(index)
                    // Extract the ID from the URL. This assumes the URL structure ends with "/id/".
                    // Consider a more robust way to get the ID if the URL format can vary.
                    val urlId = characterItem?.url?.split("/")?.last()
                    val name = characterItem?.name

                    Text(
                        text = name ?: "", // Display name, or empty string if null.
                        fontFamily = customFonts,
                        fontSize = resultTextSize,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth() // Make Text take full width for consistent click area
                            .clickable {
                                // Navigate to details screen, passing type, ID, and name.
                                // Ensure urlId and name are properly encoded if they can contain special characters.
                                navController.navigate("details/$optionSelected/$urlId/$name")
                            }
                    )
                }
            }

            planet -> {
                // Display list of planets.
                items(allPlanets?.size ?: 0) { index ->
                    val planetItem = allPlanets?.getOrNull(index)
                    val urlId = planetItem?.url?.split("/")?.last()
                    val name = planetItem?.name

                    Text(
                        text = name ?: "",
                        fontFamily = customFonts,
                        fontSize = resultTextSize,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("details/$optionSelected/$urlId/$name")
                            }
                    )
                }
            }

            ship -> {
                // Display list of vehicles/ships.
                items(allVehicles?.size ?: 0) { index ->
                    val vehicleItem = allVehicles?.getOrNull(index)
                    val urlId = vehicleItem?.url?.split("/")?.last()
                    val name = vehicleItem?.name

                    Text(
                        text = name ?: "",
                        fontFamily = customFonts,
                        fontSize = resultTextSize,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate("details/$optionSelected/$urlId/$name")
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun ListResultsWithCheckBox(
    allCharacters: List<CharactersItem>?,
    navController: NavHostController,
    itemId: String?
) {
    val resultTextSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 20).sp

    // Use a mutableStateMapOf to store the checked state for each item.
    // The key could be the character's URL (assuming it's unique) or any other unique ID.
    val checkedStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(allCharacters?.size ?: 0) { index ->
            val characterItem = allCharacters?.getOrNull(index)
            val characterKey = characterItem?.url ?: index.toString() // Use URL or index as a key
            val name = characterItem?.name

            // Get the current checked state for this item, defaulting to false if not present.
            val isChecked = checkedStates[characterKey] == true

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clickable { // Make the whole row clickable to toggle the checkbox
                        // Uncheck all other items
                        checkedStates.keys.forEach { key ->
                            if (key != characterKey) {
                                checkedStates[key] = false
                            }
                        }
                        // Toggle the current item's checked state
                        checkedStates[characterKey] = !isChecked
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start // Adjusted for better alignment
            ) {
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { newCheckedState ->
                        // Uncheck all other items
                        checkedStates.keys.forEach { key ->
                            if (key != characterKey) {
                                checkedStates[key] = false
                            }
                        }
                        // Set the current item's checked state
                        checkedStates[characterKey] = newCheckedState
                    },
                    modifier = Modifier.padding(end = 16.dp) // Increased padding for better spacing
                )
                Text(
                    text = name ?: "",
                    fontFamily = customFonts,
                    fontSize = resultTextSize,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start, // Align text to the start
                    modifier = Modifier
                        .weight(1f) // Allow text to take remaining space
                        .clickable{
                            if (isChecked) {
                                val urlId = characterItem?.url?.split("/")?.last()
                                navController.navigate("compareCharacters/$itemId/$urlId")
                            }
                        }
                )
            }
        }
    }
}