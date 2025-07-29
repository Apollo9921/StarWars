package com.example.starwars.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.TopBarWithImage
import com.example.starwars.core.customFonts
import com.example.starwars.utils.size.ScreenSizeUtils

/**
 * Composable function that represents the main home screen of the Star Wars application.
 *
 * This screen displays an introduction to the app and provides navigation options
 * to different sections like Characters, Ships, and Planets.
 * It uses a [Scaffold] to provide a basic structure with a top bar.
 *
 * @param navController The [NavHostController] used for navigating to other screens.
 */
@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(), // Scaffold fills the entire available screen space.
        containerColor = Color.Transparent, // Set container color, Transparent for underlying background if any.
        topBar = {
            // Display a top bar with the app logo. Back navigation is disabled.
            TopBarWithImage(isBackEnabled = false, imageResId = R.drawable.logo)
        },
        content = { innerPadding ->
            // Pass the inner padding provided by Scaffold to the content.
            // This ensures content is not obscured by the top bar.
            HomeScreenContent(navController = navController, paddingValues = innerPadding)
        }
    )
}

/**
 * Private composable function that defines the main content of the [HomeScreen].
 *
 * It displays introductory text and a series of buttons for navigating to
 * different categories of Star Wars entities.
 *
 * @param navController The [NavHostController] for navigation.
 * @param paddingValues The [PaddingValues] from the parent [Scaffold] to correctly position content.
 */
@Composable
private fun HomeScreenContent(navController: NavHostController, paddingValues: PaddingValues) {
    // List of button options, using string resources for localization.
    val buttonOptions = listOf(
        stringResource(R.string.characters),
        stringResource(R.string.ships),
        stringResource(R.string.planets)
    )

    // Calculate dynamic font sizes based on screen width for responsive UI.
    val titleFontSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).sp
    val descriptionFontSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 32).sp
    val buttonOptionsTextFontSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 32).sp

    Column(
        modifier = Modifier
            .fillMaxSize() // Column takes up all available space.
            // Apply padding: top from Scaffold, and start/end for horizontal margins.
            .padding(top = paddingValues.calculateTopPadding(), start = 25.dp, end = 25.dp)
            .verticalScroll(rememberScrollState()), // Make the column scrollable if content exceeds screen height.
        horizontalAlignment = Alignment.CenterHorizontally, // Center children horizontally.
    ) {
        // App introductory title.
        Text(
            text = stringResource(R.string.app_intro),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = customFonts,
            fontSize = titleFontSize,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp // Specify line height for better text readability.
        )
        Spacer(modifier = Modifier.padding(20.dp)) // Vertical spacing.

        // App introductory description.
        Text(
            text = stringResource(R.string.app_intro_description),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = customFonts,
            fontSize = descriptionFontSize,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.padding(20.dp)) // Vertical spacing.

        // Inner Column for buttons, arranged at the bottom.
        // This pushes the buttons towards the bottom of the available space in the outer Column.
        Column(
            modifier = Modifier
                .fillMaxSize() // Takes remaining space in the parent Column.
                .padding(start = 10.dp, end = 10.dp), // Horizontal padding for buttons.
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom // Arrange buttons at the bottom.
        ) {
            buttonOptions.forEach { optionText ->
                Button(
                    onClick = {
                        // Navigate to the search screen, passing the selected option as an argument.
                        // Example route: "search/Characters"
                        navController.navigate("search/$optionText")
                    },
                    shape = RoundedCornerShape(percent = 30), // Apply rounded corners to the button.
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary, // Button background color.
                        contentColor = MaterialTheme.colorScheme.secondary // Button text/icon color.
                    ),
                    modifier = Modifier.fillMaxWidth(), // Button takes the full available width.
                    content = {
                        Text(
                            text = optionText,
                            fontFamily = customFonts,
                            fontSize = buttonOptionsTextFontSize,
                            textAlign = TextAlign.Center
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(10.dp)) // Spacing between buttons.
            }
        }
    }
}