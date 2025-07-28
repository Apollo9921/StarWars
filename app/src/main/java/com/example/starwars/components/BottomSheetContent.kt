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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
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
import com.example.starwars.networking.viewModel.SearchViewModel
import com.example.starwars.screens.allCharacters
import com.example.starwars.utils.filter.Filter.filterCharacters
import com.example.starwars.utils.size.ScreenSizeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private var listSpeciesSelected: SnapshotStateList<String> = emptyList<String>().toMutableStateList()
private var listGenderSelected: SnapshotStateList<String> = emptyList<String>().toMutableStateList()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    viewModel: SearchViewModel?
) {
    val filterOptionsText = ScreenSizeUtils.calculateCustomWidth(baseSize = 15).sp
    val imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 25).dp

    val specieFilterOptions = listOf(
        "Human",
        "Droid",
        "Ewoks",
        "Keshiri",
        "Zeltrons",
        "Jawas"
    )

    val genderFilterOptions = listOf(
        "Male",
        "Female",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sliders),
                contentDescription = "Star Wars Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(imageSize)
                    .padding(start = 50.dp)
            )
            Text(
                text = stringResource(R.string.filter_reset),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                fontSize = filterOptionsText,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.clickable {
                    listSpeciesSelected.clear()
                    listGenderSelected.clear()
                    viewModel?.filteredCharacters = emptyList()
                    allCharacters = viewModel?.allCharacters?.toMutableStateList()
                }
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        OptionsByType(specieFilterOptions, stringResource(R.string.filter_specie_option))
        Spacer(modifier = Modifier.padding(20.dp))
        OptionsByType(genderFilterOptions, stringResource(R.string.filter_gender_option))
        Spacer(modifier = Modifier.padding(20.dp))
        SearchButton(scope, scaffoldState, viewModel)
    }
}

@Composable
private fun OptionsByType(filterOptionsText: List<String>, text: String) {
    val textSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 15).sp
    val specie = stringResource(R.string.filter_specie_option)
    val gender = stringResource(R.string.filter_gender_option)
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        fontFamily = customFonts,
        fontSize = textSize,
        textAlign = TextAlign.Center,
        lineHeight = 32.sp
    )
    FlowRow(
        maxItemsInEachRow = 6,
    ) {
        filterOptionsText.forEach { txt ->
            val isSelected = remember { mutableStateOf(false) }
            Button(
                onClick = {
                    isSelected.value = !isSelected.value
                    if (text == specie) {
                        if (isSelected.value) {
                            listSpeciesSelected.add(txt)
                        } else {
                            listSpeciesSelected.remove(txt)
                        }
                    } else if (text == gender) {
                        if (isSelected.value) {
                            listGenderSelected.add(txt)
                        } else {
                            listGenderSelected.remove(txt)
                        }
                    }
                },
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                shape = RoundedCornerShape(percent = 50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (listSpeciesSelected.contains(txt) || listGenderSelected.contains(txt)) Primary else Tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = txt,
                    maxLines = 1,
                    fontFamily = customFonts,
                    fontSize = textSize,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchButton(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    viewModel: SearchViewModel?
) {
    val textSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).sp
    Button(
        onClick = {
            val filteredCharacters =
                filterCharacters(listSpeciesSelected, listGenderSelected, viewModel!!)

            allCharacters?.clear()
            filteredCharacters.let {
                it?.let { elements -> allCharacters?.addAll(elements) }
            }

            viewModel.filteredCharacters = filteredCharacters ?: emptyList()

            if(filteredCharacters.isNullOrEmpty()){
                viewModel.filteredCharacters = emptyList()
            }

            //TODO when filtering also sorting to the corresponding option is selected

            scope.launch { scaffoldState.bottomSheetState.hide() }
        },
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.fillMaxWidth()
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