package com.example.starwars.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.TopBarWithText
import com.example.starwars.core.customFonts
import com.example.starwars.utils.size.ScreenSizeUtils

@Composable
fun CompareCharactersScreen(navController: NavHostController, itemId1: String?, itemId2: String?) {
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
                CompareCharactersContent()
            }
        }
    )
}

@Composable
private fun CompareCharactersContent() {
    val characterInfoSize = ScreenSizeUtils.calculateCustomWidth(10).sp

    val characterTitles = listOf(
        stringResource(R.string.details_character_name),
        stringResource(R.string.details_character_height),
        stringResource(R.string.details_character_mass),
        stringResource(R.string.details_character_gender),
        stringResource(R.string.details_character_eye_color),
        stringResource(R.string.details_character_hair_color)
    )

    // Replace with your actual character data
    val characterInfo1 = listOf(
        "Luke Skywalker", "172", "77", "male", "blue", "blond"
    )

    val characterInfo2 = listOf(
        "Darth Vader", "202", "136", "male", "yellow", "none"
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround // Adjust as needed
    ) {
        // Character 1 Info
        CharacterInfoColumn(
            titles = characterTitles,
            info = characterInfo1,
            characterName = characterInfo1[0], // Assuming the first item is the name
            infoTextSize = characterInfoSize
        )

        Spacer(modifier = Modifier.width(16.dp)) // Add some space between the columns

        // Character 2 Info
        CharacterInfoColumn(
            titles = characterTitles,
            info = characterInfo2,
            characterName = characterInfo2[0], // Assuming the first item is the name
            infoTextSize = characterInfoSize
        )
    }
}

@Composable
private fun CharacterInfoColumn(
    titles: List<String>,
    info: List<String>,
    characterName: String, // Added for a potential header
    infoTextSize: TextUnit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(8.dp),
        horizontalAlignment = Alignment.Start // Align text to the start
    ) {
        // Display character name as a header for each column
        Text(
            text = characterName,
            fontSize = (infoTextSize.value + 5).sp, // Slightly larger for the name
            color = MaterialTheme.colorScheme.primary,
            fontFamily = customFonts,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        titles.forEachIndexed { index, title ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = "$title: ",
                    fontSize = infoTextSize,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = info.getOrElse(index) { "N/A" }, // Provide a default if info is missing
                    fontSize = infoTextSize,
                    color = MaterialTheme.colorScheme.primary,
                    fontFamily = customFonts,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}