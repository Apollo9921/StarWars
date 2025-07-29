package com.example.starwars.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starwars.screens.DetailsScreen
import com.example.starwars.screens.HomeScreen
import com.example.starwars.screens.SearchScreen

@Composable
fun BasicNavigation() {
    val navController: NavHostController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = Screen.Search.route) {
            val optionSelected =
                navController.currentBackStackEntry?.arguments?.getString("optionSelected")
            SearchScreen(navController = navController, optionSelected = optionSelected ?: "")
        }
        composable(route = Screen.Details.route) {
            val optionSelected =
                navController.currentBackStackEntry?.arguments?.getString("optionSelected")
            val itemId = navController.currentBackStackEntry?.arguments?.getString("itemId")
            val name = navController.currentBackStackEntry?.arguments?.getString("name")
            DetailsScreen(
                navController = navController,
                optionSelected = optionSelected ?: "",
                itemId = itemId ?: "",
                name = name ?: ""
            )
        }
    }
}