package com.example.starwars.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.TopBarWithText

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CompareCharacters(navController: NavHostController, itemId1: String?, itemId2: String?) {
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

        }
    )
}