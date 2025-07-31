package com.example.starwars.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search/{optionSelected}")
    object Details : Screen("details/{optionSelected}/{itemId}/{name}")
    object ChooseCharacter : Screen("chooseCharacter/{itemId}/{name}")
    object CompareCharacters : Screen("compareCharacters/{itemId1}/{itemId2}")
}