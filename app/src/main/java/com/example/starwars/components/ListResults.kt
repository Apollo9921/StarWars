package com.example.starwars.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun ListResults(
    allCharacters: List<CharactersItem>?,
    allPlanets: List<PlanetsItem>?,
    allVehicles: List<ShipsItem>?,
    optionSelected: String,
    navController: NavHostController
) {
    val resultTextSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 20).sp

    val character = stringResource(R.string.characters)
    val planet = stringResource(R.string.planets)
    val ship = stringResource(R.string.ships)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 25.dp, end = 25.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when(optionSelected) {
            character -> {
                items(allCharacters?.size ?: 0) { characterItem ->
                    val url = allCharacters?.get(characterItem)?.url?.split("/")?.last()
                    val name = allCharacters?.get(characterItem)?.name
                    Text(
                        text = name ?: "",
                        fontFamily = customFonts,
                        fontSize = resultTextSize,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("details/$optionSelected/$url/$name")
                            }
                    )
                }
            }

            planet -> {
                items(allPlanets?.size ?: 0) { planetItem ->
                    val url = allPlanets?.get(planetItem)?.url?.split("/")?.last()
                    val name = allPlanets?.get(planetItem)?.name
                    Text(
                        text = name ?: "",
                        fontFamily = customFonts,
                        fontSize = resultTextSize,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("details/$optionSelected/$url/$name")
                            }
                    )
                }
            }

            ship -> {
                items(allVehicles?.size ?: 0) { vehicleItem ->
                    val url = allVehicles?.get(vehicleItem)?.url?.split("/")?.last()
                    val name = allVehicles?.get(vehicleItem)?.name
                    Text(
                        text = name ?: "",
                        fontFamily = customFonts,
                        fontSize = resultTextSize,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .clickable {
                                navController.navigate("details/$optionSelected/$url/$name")
                            }
                    )
                }
            }
        }
    }
}