package com.example.starwars.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.viewModel.CompareCharactersViewModel
import com.example.starwars.utils.network.ConnectivityObserver
import com.example.starwars.utils.size.ScreenSizeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private var viewModel: CompareCharactersViewModel? = null
private var characterDetails1: CharactersItem? = null
private var characterDetails2: CharactersItem? = null

@Composable
fun CompareCharactersScreen(navController: NavHostController, itemId1: String?, itemId2: String?) {
    viewModel = koinViewModel<CompareCharactersViewModel>()
    val networkStatus = viewModel?.networkStatus?.collectAsState()
    var isConnected = remember { mutableStateOf(false) }
    checkIfIsConnected(networkStatus, isConnected, itemId1, itemId2)

    val isLoading = viewModel?.isLoading?.value
    var isSuccess = viewModel?.isSuccess?.value
    val isError = viewModel?.isError?.value
    val errorMessage = viewModel?.errorMessage?.value

    characterDetails1 = viewModel?.characterDetails1
    characterDetails2 = viewModel?.characterDetails2

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopBarWithText(
                isBackEnabled = true,
                onBackClick = { navController.navigateUp() },
                text = stringResource(R.string.compare_screen_title)
            )
        },
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = it.calculateTopPadding() + 50.dp,
                        start = 25.dp,
                        end = 25.dp,
                        bottom = 60.dp
                    )
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
                        CompareCharactersContent(characterDetails1, characterDetails2)
                    }
                }

            }
        }
    )
}

@Composable
private fun CompareCharactersContent(
    characterDetails1: CharactersItem?,
    characterDetails2: CharactersItem?
) {
    val characterInfoSize = ScreenSizeUtils.calculateCustomWidth(13).sp

    val titlesAndValues1 = listOf(
        stringResource(R.string.details_character_name) to (characterDetails1?.name ?: "N/A"),
        stringResource(R.string.details_character_height) to (characterDetails1?.height ?: "N/A"),
        stringResource(R.string.details_character_mass) to (characterDetails1?.mass ?: "N/A"),
        stringResource(R.string.details_character_gender) to (characterDetails1?.gender ?: "N/A"),
        stringResource(R.string.details_character_eye_color) to (characterDetails1?.eye_color ?: "N/A"),
        stringResource(R.string.details_character_hair_color) to (characterDetails1?.hair_color ?: "N/A")
    )

    val values2 = listOf(
        characterDetails2?.name ?: "N/A",
        characterDetails2?.height ?: "N/A",
        characterDetails2?.mass ?: "N/A",
        characterDetails2?.gender ?: "N/A",
        characterDetails2?.eye_color ?: "N/A",
        characterDetails2?.hair_color ?: "N/A"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()) // Scroll the whole comparison content if it's too long
            .padding(horizontal = 8.dp) // Add some horizontal padding to the overall column
    ) {
        // Headers for Character Names
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween // Or SpaceAround
        ) {
            Text( // Spacer for title column
                text = "",
                modifier = Modifier.weight(1f) // Adjust weight as needed
            )
            Text(
                text = characterDetails1?.name ?: "Character 1",
                fontSize = (characterInfoSize.value + 4).sp,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1.5f).padding(horizontal = 4.dp)
            )
            Text(
                text = characterDetails2?.name ?: "Character 2",
                fontSize = (characterInfoSize.value + 4).sp,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1.5f).padding(horizontal = 4.dp)
            )
        }

        // Attribute rows
        titlesAndValues1.forEachIndexed { index, (title, value1) ->
            val value2 = values2.getOrElse(index) { "N/A" }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .defaultMinSize(minHeight = (characterInfoSize.value * 2.5).dp), // Ensure a minimum height for rows
                verticalAlignment = Alignment.CenterVertically // Center content vertically within the row
            ) {
                Text(
                    text = "$title:",
                    fontSize = characterInfoSize,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.weight(1f) // Adjust weight
                )
                Text(
                    text = value1,
                    fontSize = characterInfoSize,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1.5f).padding(horizontal = 4.dp) // Adjust weight
                )
                Text(
                    text = value2,
                    fontSize = characterInfoSize,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1.5f).padding(horizontal = 4.dp) // Adjust weight
                )
            }
        }
    }
}

private fun checkIfIsConnected(
    networkStatus: State<ConnectivityObserver.Status>?,
    isConnected: MutableState<Boolean>,
    itemId1: String?,
    itemId2: String?
) {
    if (networkStatus?.value == ConnectivityObserver.Status.Available && !isConnected.value) {
        isConnected.value = true
        CoroutineScope(Dispatchers.IO).launch {
            if (characterDetails1 == null || characterDetails2 == null) {
                async { viewModel?.getCharacter(itemId1?.toInt()) }
                async { viewModel?.getCharacter(itemId2?.toInt()) }
            }
        }
    } else if (networkStatus?.value == ConnectivityObserver.Status.Unavailable && !isConnected.value) {
        viewModel?.isError?.value = true
        viewModel?.errorMessage?.value = "No internet connection"
    }
}