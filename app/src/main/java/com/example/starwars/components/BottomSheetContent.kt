package com.example.starwars.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwars.R
import com.example.starwars.core.Primary
import com.example.starwars.core.Tertiary
import com.example.starwars.core.customFonts
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.species.SpeciesItem
import com.example.starwars.networking.viewModel.SearchViewModel
import com.example.starwars.screens.allCharacters
import com.example.starwars.utils.filter.Filter.filterCharacters
import com.example.starwars.utils.size.ScreenSizeUtils
import com.example.starwars.utils.sort.Sorting
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private var listSpeciesSelected: SnapshotStateList<String> = mutableStateListOf()
private var listGenderSelected: SnapshotStateList<String> = mutableStateListOf()

/**
 * Composable function that defines the content for a bottom sheet used for filtering characters.
 *
 * It allows users to select species and gender filters and then apply these filters
 * to a list of characters. The results are updated in the provided [SearchViewModel]
 * and a global `allCharacters` list.
 *
 * @param scope A [CoroutineScope] used for launching coroutines, typically for actions like hiding the bottom sheet.
 * @param scaffoldState The [BottomSheetScaffoldState] used to control the bottom sheet (e.g., hiding it).
 * @param viewModel The [SearchViewModel] instance that holds character data and filter state. Can be null.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    allCharactersSaved: SnapshotStateList<CharactersItem>?,
    allSpecies: SnapshotStateList<SpeciesItem>?,
    viewModel: SearchViewModel?,
    sortOptionNameYearSelected: Int,
    sortOptionSelected: Int
) {
    //TODO remove this two lists, but only when use leaves the search screen
    //listSpeciesSelected.removeAll(listSpeciesSelected)
    //listGenderSelected.removeAll(listGenderSelected)

    // Calculate dynamic sizes based on screen width for responsive UI.
    val filterOptionsText = ScreenSizeUtils.calculateCustomWidth(baseSize = 15).sp
    val imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 25).dp

    val specieFilterOptions = listOf(
        "Gungan",
        "Droid",
        "Wookie",
        "Rodian",
        "Zabrak",
        "Mirialan"
    )

    val genderFilterOptions = listOf(
        "Male",
        "Female",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp) // Apply overall padding to the content.
    ) {
        // Header section with an image and a reset button.
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sliders), // Filter icon.
                contentDescription = "Star Wars Logo",
                modifier = Modifier
                    .weight(1f) // Takes available space, pushing reset text to the side.
                    .size(imageSize)
                    .padding(start = 50.dp) // Adjust padding as needed.
            )
            Text(
                text = stringResource(R.string.filter_reset),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                fontSize = filterOptionsText,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.clickable {
                    // Reset selected filters and character lists.
                    listSpeciesSelected.clear()
                    listGenderSelected.clear()
                    viewModel?.filteredCharacters = emptyList()
                    // Resetting global allCharacters to the original full list from ViewModel.
                    allCharacters = allCharactersSaved
                    // Sorting to the corresponding option is selected
                    Sorting.sortCharacterWhenSearch(sortOptionNameYearSelected, sortOptionSelected)
                }
            )
        }
        Spacer(modifier = Modifier.padding(10.dp)) // Vertical spacing.

        // Display filter options for species.
        OptionsByType(specieFilterOptions, stringResource(R.string.filter_specie_option))
        Spacer(modifier = Modifier.padding(20.dp)) // Vertical spacing.

        // Display filter options for gender.
        OptionsByType(genderFilterOptions, stringResource(R.string.filter_gender_option))
        Spacer(modifier = Modifier.padding(20.dp)) // Vertical spacing.

        // Button to apply the selected filters.
        SearchButton(
            scope,
            scaffoldState,
            allCharactersSaved,
            allSpecies,
            sortOptionNameYearSelected,
            sortOptionSelected,
            viewModel
        )
    }
}

/**
 * A private composable function that displays a list of filter options (e.g., species, gender)
 * as selectable buttons within a [FlowRow].
 *
 * @param filterOptionsText A list of strings representing the filter options to display.
 * @param text The title or type of the filter group (e.g., "Species", "Gender").
 */
@Composable
private fun OptionsByType(filterOptionsText: List<String>, text: String) {
    val textSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 15).sp
    // Get string resources for comparison to determine which global list to update.
    val specie = stringResource(R.string.filter_specie_option)
    val gender = stringResource(R.string.filter_gender_option)

    // Title for the filter group.
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        fontFamily = customFonts,
        fontSize = textSize,
        textAlign = TextAlign.Center,
        lineHeight = 32.sp
    )
    // FlowRow arranges items horizontally and wraps to the next line if space is insufficient.
    FlowRow(
        maxItemsInEachRow = 6, // Customize how many items before wrapping.
    ) {
        filterOptionsText.forEach { optionText ->
            // `isSelected` state is remembered for each button individually.
            val isSelected = remember {
                mutableStateOf(
                    listSpeciesSelected.contains(optionText) || listGenderSelected.contains(
                        optionText
                    )
                )
            }

            Button(
                onClick = {
                    isSelected.value = !isSelected.value // Toggle local selection state.
                    // Update the corresponding global list based on the filter type and selection.
                    if (text == specie) {
                        if (isSelected.value) {
                            listSpeciesSelected.add(optionText)
                        } else {
                            listSpeciesSelected.remove(optionText)
                        }
                    } else if (text == gender) {
                        if (isSelected.value) {
                            listGenderSelected.add(optionText)
                        } else {
                            listGenderSelected.remove(optionText)
                        }
                    }
                },
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                shape = RoundedCornerShape(percent = 50), // Circular buttons.
                colors = ButtonDefaults.buttonColors(
                    // Button color changes based on whether the option is selected in the global lists.
                    containerColor = if (listSpeciesSelected.contains(optionText) || listGenderSelected.contains(
                            optionText
                        )
                    ) Primary else Tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = optionText,
                    maxLines = 1,
                    fontFamily = customFonts,
                    fontSize = textSize,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(4.dp)) // Spacing between buttons.
        }
    }
}

/**
 * A private composable function for the "Search" or "Apply Filters" button.
 *
 * When clicked, it filters the characters based on the selected species and gender,
 * updates the ViewModel and a global character list, and then hides the bottom sheet.
 *
 * @param scope The [CoroutineScope] to launch the action of hiding the bottom sheet.
 * @param scaffoldState The [BottomSheetScaffoldState] to control the bottom sheet.
 * @param viewModel The [SearchViewModel] to update with filtered results. Can be null.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchButton(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    allCharactersSaved: SnapshotStateList<CharactersItem>?,
    allSpecies: SnapshotStateList<SpeciesItem>?,
    sortOptionNameYearSelected: Int,
    sortOptionSelected: Int,
    viewModel: SearchViewModel?
) {
    val textSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).sp
    Button(
        onClick = {
            // Ensure viewModel is not null before proceeding, as filterCharacters requires it.
            viewModel?.let { vm ->
                val filteredCharacters =
                    filterCharacters(
                        listSpeciesSelected,
                        listGenderSelected,
                        allCharactersSaved,
                        allSpecies
                    )

                if (filteredCharacters?.isNotEmpty() == true) {
                    // Update the global allCharacters list.
                    allCharacters?.clear()
                    filteredCharacters.let { elements -> allCharacters?.addAll(elements) }

                    // Update the ViewModel's list of filtered characters.
                    vm.filteredCharacters = filteredCharacters
                } else {
                    // If filtering fails, use the original list.
                    vm.filteredCharacters = emptyList()
                    allCharacters?.clear()
                    allCharacters?.addAll(allCharactersSaved ?: emptyList())
                }

                // "when filtering also sorting to the corresponding option is selected"
                Sorting.sortCharacterWhenSearch(sortOptionNameYearSelected, sortOptionSelected)
            }

            // Hide the bottom sheet after applying filters.
            scope.launch { scaffoldState.bottomSheetState.hide() }
        },
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
        shape = RoundedCornerShape(percent = 50), // Circular button.
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.fillMaxWidth() // Button takes the full width.
    ) {
        Text(
            text = stringResource(R.string.filter_confirm_changes),
            maxLines = 1,
            fontFamily = customFonts,
            fontSize = textSize,
            textAlign = TextAlign.Center
        )
    }
}