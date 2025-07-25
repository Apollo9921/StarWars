package com.example.starwars.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
}