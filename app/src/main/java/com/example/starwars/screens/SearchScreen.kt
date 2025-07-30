package com.example.starwars.screens

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.starwars.R
import com.example.starwars.components.BottomSheetContent
import com.example.starwars.components.ListResults
import com.example.starwars.components.TopBarWithImage
import com.example.starwars.core.SearchBar
import com.example.starwars.core.SearchBarText
import com.example.starwars.core.customFonts
import com.example.starwars.networking.model.characters.CharactersItem
import com.example.starwars.networking.model.planets.PlanetsItem
import com.example.starwars.networking.model.ships.ShipsItem
import com.example.starwars.networking.viewModel.SearchViewModel
import com.example.starwars.utils.lockOrientation.LockOrientation
import com.example.starwars.utils.network.ConnectivityObserver
import com.example.starwars.utils.size.ScreenSizeUtils
import com.example.starwars.utils.sort.Sorting.sorByYear
import com.example.starwars.utils.sort.Sorting.sortAscendant
import com.example.starwars.utils.sort.Sorting.sortByName
import com.example.starwars.utils.sort.Sorting.sortCharacterWhenSearch
import com.example.starwars.utils.sort.Sorting.sortCharactersNameAscendant
import com.example.starwars.utils.sort.Sorting.sortDescendant
import com.example.starwars.utils.sort.Sorting.sortPlanetsNameAscendant
import com.example.starwars.utils.sort.Sorting.sortPlanetsWhenSearch
import com.example.starwars.utils.sort.Sorting.sortShipsNameAscendant
import com.example.starwars.utils.sort.Sorting.sortShipsWhenSearch
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

private var viewModel: SearchViewModel? = null
private var sortOptionNameYearSelected = mutableIntStateOf(0)
private var sortOptionSelected = mutableIntStateOf(0)
private var allCharactersSaved: SnapshotStateList<CharactersItem>? = mutableStateListOf<CharactersItem>()
var allCharacters: SnapshotStateList<CharactersItem>? = mutableStateListOf<CharactersItem>()
var allPlanets: SnapshotStateList<PlanetsItem>? = mutableStateListOf<PlanetsItem>()
var allShips: SnapshotStateList<ShipsItem>? = mutableStateListOf<ShipsItem>()
var option = mutableStateOf("")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController, optionSelected: String) {
    option.value = optionSelected
    val character = stringResource(R.string.characters)
    val planet = stringResource(R.string.planets)
    val ship = stringResource(R.string.ships)

    viewModel = koinViewModel<SearchViewModel>()
    var isConnected = remember { mutableStateOf(false) }
    val networkStatus = viewModel?.networkStatus?.collectAsState()
    checkIfDataIsLoaded(networkStatus, isConnected, optionSelected, character, planet, ship)

    val isLoading = viewModel?.isLoading?.value
    var isSuccess = viewModel?.isSuccess?.value
    val isError = viewModel?.isError?.value
    val errorMessage = viewModel?.errorMessage?.value

    if (allCharacters.isNullOrEmpty()) {
        allCharacters = viewModel?.allCharacters?.toMutableStateList()
        allCharactersSaved = viewModel?.allCharacters?.toMutableStateList()
    } else {
        viewModel?.isSuccess?.value = true
    }
    if (allPlanets.isNullOrEmpty()) {
        allPlanets = viewModel?.allPlanets?.toMutableStateList()
    } else {
        viewModel?.isSuccess?.value = true
    }
    if (allShips.isNullOrEmpty()) {
        allShips = viewModel?.allVehicles?.toMutableStateList()
    } else {
        viewModel?.isSuccess?.value = true
    }

    BackStack(navController)
    LockOrientation()

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
            BottomSheetContent(
                scope,
                scaffoldState,
                allCharactersSaved,
                viewModel,
                sortOptionNameYearSelected.intValue,
                sortOptionSelected.intValue
            )
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
                onBackClick = {
                    option.value = ""
                    sortOptionNameYearSelected.intValue = 0
                    sortOptionSelected.intValue = 0
                    viewModel?.filteredCharacters = emptyList()
                    navController.navigateUp()
                },
                imageResId = R.drawable.logo
            )
            Spacer(modifier = Modifier.padding(10.dp))
            SearchBar(optionSelected, scope, scaffoldState, networkStatus)
            when {
                isLoading == true -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                isError == true -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = errorMessage ?: "Unknown error",
                            color = MaterialTheme.colorScheme.primary,
                            fontFamily = customFonts,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                        )
                    }
                }

                isSuccess == true -> {
                    ListResults(allCharacters, allPlanets, allShips, optionSelected, navController)
                    SortResultByDefault(character, planet, ship, optionSelected)
                }
            }
        }
    }
}

private fun checkIfDataIsLoaded(
    networkStatus: State<ConnectivityObserver.Status>?,
    isConnected: MutableState<Boolean>,
    optionSelected: String,
    character: String,
    planet: String,
    ship: String
) {
    when {
        optionSelected == character && allCharacters.isNullOrEmpty() || optionSelected == planet && allPlanets.isNullOrEmpty() || optionSelected == ship && allShips.isNullOrEmpty() -> {
            if (networkStatus?.value == ConnectivityObserver.Status.Available && !isConnected.value) {
                isConnected.value = true
                when (option.value) {
                    character -> { viewModel?.getCharacters() }

                    planet -> { viewModel?.getPlanets() }

                    ship -> { viewModel?.getVehicles() }
                }
            }
        }
    }
}

@Composable
private fun SortResultByDefault(
    character: String,
    planet: String,
    ship: String,
    optionSelected: String
) {
    var sortFormatDefault = remember { mutableIntStateOf(0) }
    LaunchedEffect(sortFormatDefault.intValue) {
        if (sortFormatDefault.intValue == 0) {
            sortFormatDefault.intValue++
            when (optionSelected) {
                character -> {
                    sortCharactersNameAscendant()
                }

                planet -> {
                    sortPlanetsNameAscendant()
                }

                ship -> {
                    sortShipsNameAscendant()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    optionSelected: String,
    scope: CoroutineScope,
    scaffoldState: BottomSheetScaffoldState,
    networkStatus: State<ConnectivityObserver.Status>?
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

    var notInternet = remember { mutableStateOf(false) }

    if (notInternet.value) {
        Snackbar(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.secondary,
        ) {
            Text(
                text = "No internet connection",
                color = MaterialTheme.colorScheme.secondary,
                fontFamily = customFonts,
                fontSize = 16.sp,
            )
        }
        LaunchedEffect(notInternet.value) {
            delay(2000)
            notInternet.value = false
        }
    }

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
        val characters = stringResource(R.string.characters)
        val planets = stringResource(R.string.planets)
        val ships = stringResource(R.string.ships)
        TextField(
            value = searchText.value,
            onValueChange = {
                if (networkStatus?.value == ConnectivityObserver.Status.Available) {
                    searchText.value = it
                    onSearchQuery(it, characters, planets, ships, optionSelected)
                } else {
                    notInternet.value = true
                }
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

    val character = stringResource(R.string.characters)
    val planet = stringResource(R.string.planets)
    val ship = stringResource(R.string.ships)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (optionSelected == stringResource(R.string.characters)) {
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
            onClick = {
                sortOptionNameYearSelected.intValue = 0
                sortByName(optionSelected, character, planet, ship, sortOptionSelected.intValue)
            },
            contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
            shape = RoundedCornerShape(percent = 50),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (sortOptionNameYearSelected.intValue == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .weight(1f)
                .height(arrowSize)
        ) {
            Text(
                text = stringResource(R.string.sort_name),
                maxLines = 1,
                fontFamily = customFonts,
                fontSize = buttonOptionsText,
                textAlign = TextAlign.Center
            )
        }
        if (optionSelected == stringResource(R.string.characters)) {
            Spacer(modifier = Modifier.padding(4.dp))
            Button(
                onClick = {
                    sortOptionNameYearSelected.intValue = 1
                    sorByYear(sortOptionSelected.intValue)
                },
                shape = RoundedCornerShape(percent = 50),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (sortOptionNameYearSelected.intValue == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.secondary
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(arrowSize)
            ) {
                Text(
                    text = stringResource(R.string.sort_year),
                    maxLines = 1,
                    fontFamily = customFonts,
                    fontSize = buttonOptionsText,
                    textAlign = TextAlign.Center
                )
            }
        }
        Spacer(modifier = Modifier.padding(4.dp))
        Button(
            onClick = {
                sortOptionSelected.intValue = 0
                sortAscendant(
                    optionSelected,
                    character,
                    planet,
                    ship,
                    sortOptionNameYearSelected.intValue
                )
            },
            shape = RoundedCornerShape(percent = 50),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (sortOptionSelected.intValue == 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
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
            onClick = {
                sortOptionSelected.intValue = 1
                sortDescendant(
                    optionSelected,
                    character,
                    planet,
                    ship,
                    sortOptionNameYearSelected.intValue
                )
            },
            shape = RoundedCornerShape(percent = 50),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (sortOptionSelected.intValue == 1) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary,
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

@Composable
private fun BackStack(navController: NavHostController) {
    BackHandler {
        option.value = ""
        sortOptionNameYearSelected.intValue = 0
        sortOptionSelected.intValue = 0
        viewModel?.filteredCharacters = emptyList()
        navController.navigateUp()
    }
}

private fun onSearchQuery(
    it: String,
    characters: String,
    planets: String,
    ships: String,
    optionSelected: String
) {
    when (optionSelected) {
        characters -> {
            val newCharacters = viewModel?.searchCharactersByName(it)
            allCharacters?.clear()
            newCharacters?.let {
                allCharacters?.addAll(it)
            }
            sortCharacterWhenSearch(
                sortOptionNameYearSelected.intValue,
                sortOptionSelected.intValue
            )
        }

        planets -> {
            val newCharacters = viewModel?.searchPlanetsByName(it)
            allPlanets?.clear()
            newCharacters?.let {
                allPlanets?.addAll(it)
            }
            sortPlanetsWhenSearch(
                sortOptionNameYearSelected.intValue,
                sortOptionSelected.intValue
            )
        }

        ships -> {
            val newCharacters = viewModel?.searchShipsByName(it)
            allShips?.clear()
            newCharacters?.let {
                allShips?.addAll(it)
            }
            sortShipsWhenSearch(
                sortOptionNameYearSelected.intValue,
                sortOptionSelected.intValue
            )
        }
    }
}