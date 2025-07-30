package com.example.starwars.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.TopBarWithText
import com.example.starwars.core.customFonts
import com.example.starwars.networking.viewModel.DetailsViewModel
import com.example.starwars.utils.network.ConnectivityObserver
import com.example.starwars.utils.size.ScreenSizeUtils
import org.koin.androidx.compose.koinViewModel

private var viewModel: DetailsViewModel? = null

@Composable
fun DetailsScreen(
    navController: NavHostController,
    optionSelected: String,
    itemId: String,
    name: String
) {
    val character = stringResource(R.string.characters)
    val planet = stringResource(R.string.planets)
    val ship = stringResource(R.string.ships)

    viewModel = koinViewModel<DetailsViewModel>()
    var isConnected = remember { mutableStateOf(false) }
    val networkStatus = viewModel?.networkStatus?.collectAsState()
    checkIfIsConnected(networkStatus, isConnected, optionSelected, character, planet, ship, itemId)

    val isLoading = viewModel?.isLoading?.value
    var isSuccess = viewModel?.isSuccess?.value
    val isError = viewModel?.isError?.value
    val errorMessage = viewModel?.errorMessage?.value

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopBarWithText(
                isBackEnabled = true,
                onBackClick = { navController.navigateUp() },
                text = name
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding(),
                        start = 25.dp,
                        end = 25.dp,
                        bottom = 60.dp
                    )
                    .verticalScroll(rememberScrollState())
            ) {
                when {
                    isLoading == true -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }

                    isError == true -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = errorMessage ?: "Unknown error",
                                color = MaterialTheme.colorScheme.primary,
                                fontFamily = customFonts,
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    isSuccess == true -> {
                        DetailsScreenContent(optionSelected, itemId, navController)
                    }
                }
            }
        }
    )
}

@Composable
private fun DetailsScreenContent(
    optionSelected: String,
    itemId: String,
    navController: NavHostController
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 50.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        when (optionSelected) {
            "Characters" -> {
                CharacterDetailsScreen(navController, itemId)
            }

            "Planets" -> {
                PlanetDetailsScreen()
            }

            "Ships" -> {
                ShipDetailsScreen()
            }
        }
    }
}

@Composable
private fun CharacterDetailsScreen(navController: NavHostController, itemId: String) {
    val titleSize = ScreenSizeUtils.calculateCustomWidth(20).sp
    val characterInfoSize = ScreenSizeUtils.calculateCustomWidth(14).sp
    val buttonText = ScreenSizeUtils.calculateCustomWidth(baseSize = 13).sp

    val characterTitle = listOf(
        stringResource(R.string.details_character_name),
        stringResource(R.string.details_character_height),
        stringResource(R.string.details_character_mass),
        stringResource(R.string.details_character_gender),
        stringResource(R.string.details_character_eye_color),
        stringResource(R.string.details_character_hair_color)
    )

    val characterInfo = listOf(
        viewModel?.characterDetails?.name ?: "",
        "${viewModel?.characterDetails?.height} cm",
        "${viewModel?.characterDetails?.mass} kg",
        viewModel?.characterDetails?.gender ?: "",
        viewModel?.characterDetails?.eye_color ?: "",
        viewModel?.characterDetails?.hair_color ?: ""
    )

    Text(
        text = stringResource(R.string.details_character_title),
        color = MaterialTheme.colorScheme.primary,
        fontFamily = customFonts,
        fontSize = titleSize,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.padding(10.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        characterInfo.forEachIndexed { index, character ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = characterTitle[index],
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    fontSize = characterInfoSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = character,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    fontSize = characterInfoSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
    Spacer(modifier = Modifier.padding(20.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                navController.navigate("chooseCharacter/$itemId")
            },
            shape = RoundedCornerShape(percent = 30), // Apply rounded corners to the button.
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary, // Button background color.
                contentColor = MaterialTheme.colorScheme.secondary // Button text/icon color.
            ),
            modifier = Modifier.fillMaxWidth(), // Button takes the full available width.
            content = {
                Text(
                    text = stringResource(R.string.details_button_text),
                    fontFamily = customFonts,
                    fontSize = buttonText,
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}

@Composable
private fun ShipDetailsScreen() {
    val titleSize = ScreenSizeUtils.calculateCustomWidth(20).sp
    val characterInfoSize = ScreenSizeUtils.calculateCustomWidth(14).sp

    val shipTitle = listOf(
        stringResource(R.string.details_ship_name),
        stringResource(R.string.details_ship_model),
        stringResource(R.string.details_ship_manufacturer),
        stringResource(R.string.details_ship_passengers),
    )

    val shipInfo = listOf(
        viewModel?.vehicleDetails?.name ?: "",
        viewModel?.vehicleDetails?.model ?: "",
        viewModel?.vehicleDetails?.manufacturer ?: "",
        viewModel?.vehicleDetails?.passengers ?: "",
    )

    Text(
        text = stringResource(R.string.details_ship_title),
        color = MaterialTheme.colorScheme.primary,
        fontFamily = customFonts,
        fontSize = titleSize,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.padding(10.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        shipInfo.forEachIndexed { index, ship ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = shipTitle[index],
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    fontSize = characterInfoSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = ship,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    fontSize = characterInfoSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun PlanetDetailsScreen() {
    val titleSize = ScreenSizeUtils.calculateCustomWidth(20).sp
    val characterInfoSize = ScreenSizeUtils.calculateCustomWidth(14).sp

    val planetTitle = listOf(
        stringResource(R.string.details_planet_name),
        stringResource(R.string.details_planet_population),
        stringResource(R.string.details_planet_terrain),
        stringResource(R.string.details_planet_climate),
    )

    val planetInfo = listOf(
        viewModel?.planetDetails?.name ?: "",
        viewModel?.planetDetails?.population ?: "",
        viewModel?.planetDetails?.terrain ?: "",
        viewModel?.planetDetails?.climate ?: "",
    )

    Text(
        text = stringResource(R.string.details_planet_title),
        color = MaterialTheme.colorScheme.primary,
        fontFamily = customFonts,
        fontSize = titleSize,
        textAlign = TextAlign.Center
    )
    Spacer(modifier = Modifier.padding(10.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        planetInfo.forEachIndexed { index, planet ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = planetTitle[index],
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    fontSize = characterInfoSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = planet,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    fontSize = characterInfoSize,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

private fun checkIfIsConnected(
    networkStatus: State<ConnectivityObserver.Status>?,
    isConnected: MutableState<Boolean>,
    optionSelected: String,
    character: String,
    planet: String,
    ship: String,
    itemId: String
) {
    if (networkStatus?.value == ConnectivityObserver.Status.Available && !isConnected.value) {
        isConnected.value = true
        when (optionSelected) {
            character -> {
                if (viewModel?.characterDetails == null) {
                    viewModel?.getCharacter(itemId.toInt())
                }
            }

            planet -> {
                if (viewModel?.planetDetails == null) {
                    viewModel?.getPlanet(itemId.toInt())
                }
            }

            ship -> {
                if (viewModel?.vehicleDetails == null) {
                    viewModel?.getShip(itemId.toInt())
                }
            }
        }
    } else if (networkStatus?.value == ConnectivityObserver.Status.Unavailable && !isConnected.value) {
        viewModel?.isError?.value = true
        viewModel?.errorMessage?.value = "No internet connection"
    }
}