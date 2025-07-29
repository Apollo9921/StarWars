package com.example.starwars.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import com.example.starwars.components.TopBarWithText

@Composable
fun DetailsScreen(
    navController: NavHostController,
    optionSelected: String,
    itemId: String,
    name: String
) {
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
                    .padding(top = it.calculateTopPadding())
            ) {
                DetailsScreenContent(optionSelected, itemId)
            }
        }
    )
}

@Composable
private fun DetailsScreenContent(optionSelected: String, itemId: String) {

}