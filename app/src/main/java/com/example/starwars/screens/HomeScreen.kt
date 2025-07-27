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

@Composable
fun HomeScreen(navController: NavHostController) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.Transparent,
        topBar = { TopBarWithImage(isBackEnabled = false, imageResId = R.drawable.logo) },
        content = {
            HomeScreenContent(navController = navController, paddingValues = it)
        }
    )
}

@Composable
private fun HomeScreenContent(navController: NavHostController, paddingValues: PaddingValues) {
    val buttonOptions = listOf(
        stringResource(R.string.characters),
        stringResource(R.string.ships),
        stringResource(R.string.planets)
    )

    val title = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).sp
    val description = ScreenSizeUtils.calculateCustomWidth(baseSize = 32).sp
    val buttonOptionsText = ScreenSizeUtils.calculateCustomWidth(baseSize = 32).sp

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = paddingValues.calculateTopPadding(), start = 25.dp, end = 25.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.app_intro),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = customFonts,
            fontSize = title,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Text(
            text = stringResource(R.string.app_intro_description),
            color = MaterialTheme.colorScheme.primary,
            fontFamily = customFonts,
            fontSize = description,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.padding(20.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            buttonOptions.forEach {
                Button(
                    onClick = {
                        navController.navigate("search/$it")
                    },
                    shape = RoundedCornerShape(percent = 30),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.secondary
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    content = {
                        Text(
                            text = it,
                            fontFamily = customFonts,
                            fontSize = buttonOptionsText,
                            textAlign = TextAlign.Center
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(10.dp))
            }
        }
    }
}