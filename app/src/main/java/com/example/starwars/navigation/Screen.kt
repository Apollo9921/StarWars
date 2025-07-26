package com.example.starwars.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Search : Screen("search/{optionSelected}")
}