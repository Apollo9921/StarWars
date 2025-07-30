package com.example.starwars.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.ListResultsWithCheckBox
import com.example.starwars.components.TopBarWithText
import com.example.starwars.networking.model.characters.CharactersItem

private var allCharactersToSearch: SnapshotStateList<CharactersItem>? = mutableStateListOf<CharactersItem>()

@Composable
fun ChooseCharacter(navController: NavHostController, itemId: String?) {
    allCharactersToSearch = allCharactersSaved
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = {
            TopBarWithText(
                isBackEnabled = true,
                onBackClick = { navController.navigateUp() },
                text = stringResource(R.string.search_screen_title)
            )
        },
        content = {
            SearchToCompareScreenContent(navController, itemId, it)
        }
    )
}

@Composable
private fun SearchToCompareScreenContent(
    navController: NavHostController,
    itemId: String?,
    paddingValues: PaddingValues
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = paddingValues.calculateTopPadding(), start = 25.dp, end = 25.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.padding(20.dp))
        ListResultsWithCheckBox(
            allCharacters = allCharactersToSearch,
            itemId = itemId,
            navController = navController
        )
    }
}