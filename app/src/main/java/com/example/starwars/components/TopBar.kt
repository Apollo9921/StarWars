package com.example.starwars.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.starwars.R
import com.example.starwars.core.customFonts
import com.example.starwars.utils.size.ScreenSizeUtils

/**
 * A Composable function that displays a top bar with an image.
 *
 * It can optionally include a back button. If the back button is enabled,
 * the image is positioned to the side; otherwise, the image is centered.
 * The sizes of the elements are calculated dynamically based on screen width.
 *
 * @param isBackEnabled A boolean indicating whether the back button should be displayed. Defaults to true.
 * @param onBackClick A lambda function to be invoked when the back button is clicked. Defaults to an empty lambda.
 * @param imageResId The drawable resource ID for the main image to be displayed in the top bar.
 */
@Composable
fun TopBarWithImage(
    isBackEnabled: Boolean = true,
    onBackClick: () -> Unit = {},
    @DrawableRes imageResId: Int // Annotate with @DrawableRes for type safety
) {
    // Calculate initial image size. This size is used if isBackEnabled is false.
    var imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 118).dp
    val backButtonSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).dp

    if (isBackEnabled) {
        // Adjust image size if back button is enabled to make space for it.
        imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 70).dp
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 25.dp, end = 25.dp), // Apply padding.
            horizontalArrangement = Arrangement.Start, // Align items to the start.
            verticalAlignment = Alignment.CenterVertically // Center items vertically.
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary), // Tint the back button.
                modifier = Modifier
                    .size(backButtonSize)
                    .clickable { onBackClick() } // Make the back button clickable.
            )
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Star Wars Logo",
                modifier = Modifier
                    .weight(1f) // Image takes remaining space to center itself relative to the back button.
                    .size(imageSize)
            )
        }
    } else {
        // Layout for when the back button is not enabled.
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp), // Different top padding when no back button.
            horizontalArrangement = Arrangement.Center, // Center the image horizontally.
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Star Wars Logo",
                modifier = Modifier.size(imageSize)
            )
        }
    }
}

/**
 * A Composable function that displays a top bar with a text title.
 *
 * It can optionally include a back button. If the back button is enabled,
 * the text is positioned to the side; otherwise, the text is centered.
 * The sizes of the elements are calculated dynamically based on screen width.
 *
 * @param isBackEnabled A boolean indicating whether the back button should be displayed. Defaults to true.
 * @param onBackClick A lambda function to be invoked when the back button is clicked. Defaults to an empty lambda.
 * @param text The string to be displayed as the title in the top bar.
 */
@Composable
fun TopBarWithText(
    isBackEnabled: Boolean = true,
    onBackClick: () -> Unit = {},
    text: String
) {
    val titleSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 20).sp
    val backButtonSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).dp

    if (isBackEnabled) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp, start = 25.dp, end = 25.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back",
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                modifier = Modifier
                    .size(backButtonSize)
                    .clickable { onBackClick() }
            )
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                fontSize = titleSize,
                textAlign = TextAlign.Center, // Center text within its allocated space.
                lineHeight = 32.sp,
                modifier = Modifier.weight(1f) // Text takes remaining space to center itself.
            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                color = MaterialTheme.colorScheme.primary,
                fontFamily = customFonts,
                fontSize = titleSize,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp,
                modifier = Modifier.weight(1f)
            )
        }
    }
}