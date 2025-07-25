package com.example.starwars.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.TopBarWithImage

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        containerColor = Color.Transparent,
        topBar = { TopBarWithImage(isBackEnabled = false, imageResId = R.drawable.logo) },
        content = {
            HomeScreenContent(navController = navController, paddingValues = it)
        }
    )
}

@Composable
private fun HomeScreenContent(navController: NavHostController, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Here you will find all the information you need  about your favorite characters, their ships and their planets",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
    }
}