package com.example.starwars.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.starwars.R
import com.example.starwars.utils.size.ScreenSizeUtils

@Composable
fun TopBarWithImage(isBackEnabled: Boolean = true, onBackClick: () -> Unit = {}, imageResId: Int) {
    var imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 118).dp
    val back = ScreenSizeUtils.calculateCustomWidth(baseSize = 24).dp

    if (isBackEnabled) {
        imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 70).dp
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
                    .size(back)
                    .clickable { onBackClick() }
            )
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Star Wars Logo",
                modifier = Modifier
                    .weight(1f)
                    .size(imageSize)
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
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = "Star Wars Logo",
                modifier = Modifier.size(imageSize)
            )
        }
    }
}