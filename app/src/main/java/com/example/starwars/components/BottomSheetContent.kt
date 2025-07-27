package com.example.starwars.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwars.R
import com.example.starwars.core.customFonts
import com.example.starwars.utils.size.ScreenSizeUtils

@Composable
fun BottomSheetContent() {
    val filterOptionsText = ScreenSizeUtils.calculateCustomWidth(baseSize = 15).sp
    val imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 25).dp

    val specieFilterOptions = listOf(
        "Human",
        "Droid",
        "Ewoks",
        "Keshiri",
        "Zeltrons",
        "Jawas"
    )

    val genderFilterOptions = listOf(
        "Male",
        "Female",
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(25.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.sliders),
                contentDescription = "Star Wars Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(imageSize)
                    .padding(start = 50.dp)
            )
            Text(
                text = stringResource(R.string.filter_reset),
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                fontSize = filterOptionsText,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )
        }
        Spacer(modifier = Modifier.padding(10.dp))
        OptionsByType(specieFilterOptions, stringResource(R.string.filter_specie_option))
        Spacer(modifier = Modifier.padding(20.dp))
        OptionsByType(genderFilterOptions, stringResource(R.string.filter_gender_option))
        Spacer(modifier = Modifier.padding(20.dp))
        SearchButton()
    }
}

@Composable
private fun OptionsByType(filterOptionsText: List<String>, text: String) {
    val textSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 15).sp
    Text(
        text = text,
        color = MaterialTheme.colorScheme.primary,
        fontFamily = customFonts,
        fontSize = textSize,
        textAlign = TextAlign.Center,
        lineHeight = 32.sp
    )
    FlowRow(
        maxItemsInEachRow = 6,
    ) {
        filterOptionsText.forEach { txt ->
            Button(
                onClick = { /* ... */ },
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                shape = RoundedCornerShape(percent = 50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(
                    text = txt,
                    maxLines = 1,
                    fontFamily = customFonts,
                    fontSize = textSize,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
private fun SearchButton() {
    val textSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).sp
    Button(
        onClick = { /* ... */ },
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
        shape = RoundedCornerShape(percent = 50),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.filter_confirm_changes),
            maxLines = 1,
            fontFamily = customFonts,
            fontSize = textSize,
            textAlign = TextAlign.Center
        )
    }
}