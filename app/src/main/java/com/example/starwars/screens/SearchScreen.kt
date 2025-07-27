package com.example.starwars.screens

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.BottomSheetContent
import com.example.starwars.components.ListResults
import com.example.starwars.components.TopBarWithImage
import com.example.starwars.core.SearchBar
import com.example.starwars.core.SearchBarText
import com.example.starwars.core.customFonts
import com.example.starwars.utils.size.ScreenSizeUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, optionSelected: String) {
    val context = LocalContext.current
    val activity = context as? Activity
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            initialValue = SheetValue.Hidden,
            skipHiddenState = false
        )
    )
    val scope = rememberCoroutineScope()
    val screenHeight = ScreenSizeUtils.getScreenHeightDp()
    val sheetMaxHeight = screenHeight / 1.5f

    BottomSheetScaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        containerColor = Color.Transparent,
        sheetContent = {
            BottomSheetContent()
        },
        sheetPeekHeight = sheetMaxHeight,
        sheetContainerColor = MaterialTheme.colorScheme.secondary,
        sheetDragHandle = {
            val dragHandleSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 60).dp
            HorizontalDivider(
                color = MaterialTheme.colorScheme.primary,
                thickness = 4.dp,
                modifier = Modifier.width(dragHandleSize)
            )
        }
    ) {
        if (scaffoldState.bottomSheetState.isVisible && scaffoldState.bottomSheetState.currentValue != SheetValue.Hidden) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {}
                    )
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            TopBarWithImage(
                isBackEnabled = true,
                onBackClick = { navController.navigateUp() },
                imageResId = R.drawable.logo
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SearchBar(optionSelected, scope, scaffoldState)
            ListResults(optionSelected)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    optionSelected: String,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState
) {
    val orientation = LocalConfiguration.current.orientation
    val isPortrait = orientation == Configuration.ORIENTATION_PORTRAIT

    val title = if (isPortrait) {
        ScreenSizeUtils.calculateCustomWidth(baseSize = 32).sp
    } else {
        ScreenSizeUtils.calculateCustomWidth(baseSize = 24).sp
    }
    val searchText = remember { mutableStateOf("") }
    val searchBarWidth = ScreenSizeUtils.calculateCustomWidth(baseSize = 320).dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 25.dp, end = 25.dp)
    ) {
        Text(
            text = optionSelected,
            color = MaterialTheme.colorScheme.primary,
            fontFamily = customFonts,
            fontSize = title,
            textAlign = TextAlign.Center,
            lineHeight = 32.sp
        )
        Spacer(modifier = Modifier.padding(10.dp))
        TextField(
            value = searchText.value,
            onValueChange = {
                searchText.value = it
            },
            shape = RoundedCornerShape(percent = 36),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                disabledTextColor = SearchBarText,
                focusedTextColor = SearchBarText,
                unfocusedTextColor = SearchBarText,
                cursorColor = SearchBarText,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                disabledContainerColor = SearchBar,
                focusedContainerColor = SearchBar,
                unfocusedContainerColor = SearchBar
            ),
            trailingIcon = {
                Image(
                    painter = painterResource(id = R.drawable.close),
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clickable {
                            searchText.value = ""
                        }
                )
            },
            modifier = Modifier.width(searchBarWidth),
        )
        Spacer(modifier = Modifier.padding(10.dp))
        FilterSortRow(scope, scaffoldState, optionSelected)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FilterSortRow(
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    optionSelected: String
) {
    val imageSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 25).dp
    val buttonOptionsText = ScreenSizeUtils.calculateCustomWidth(baseSize = 13).sp
    val arrowSize = ScreenSizeUtils.calculateCustomWidth(baseSize = 30).dp
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (optionSelected == "Characters") {
            Image(
                painter = painterResource(R.drawable.sliders),
                contentDescription = "Icon 1",
                modifier = Modifier
                    .size(imageSize)
                    .clickable {
                        scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                    }
            )
        }
        Spacer(modifier = Modifier.padding(25.dp))
        Button(
            onClick = { /* ... */ },
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .weight(1f)
                .height(arrowSize)
        ) {
            Text(
                text = "NAME",
                maxLines = 1,
                fontFamily = customFonts,
                fontSize = buttonOptionsText,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            onClick = { /* ... */ },
            shape = RoundedCornerShape(percent = 50),
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .weight(1f)
                .height(arrowSize)
        ) {
            Text(
                text = "YEAR",
                maxLines = 1,
                fontFamily = customFonts,
                fontSize = buttonOptionsText,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            onClick = { /* ... */ },
            shape = RoundedCornerShape(percent = 50),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .weight(0.5f)
                .height(arrowSize)
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_up),
                contentDescription = "Up Icon",
                modifier = Modifier.size(arrowSize)
            )
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            onClick = { /* ... */ },
            shape = RoundedCornerShape(percent = 50),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .weight(0.5f)
                .height(arrowSize)
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_down),
                contentDescription = "Dropdown Icon",
                modifier = Modifier.size(arrowSize)
            )
        }
    }
}