package com.example.starwars.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.starwars.screens.DetailsScreen
import com.example.starwars.screens.HomeScreen
import com.example.starwars.screens.SearchScreen
import com.example.starwars.screens.ChooseCharacter
import com.example.starwars.screens.CompareCharactersScreen

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
        composable(route = Screen.ChooseCharacter.route) {
            val itemId = navController.currentBackStackEntry?.arguments?.getString("itemId")
            val name = navController.currentBackStackEntry?.arguments?.getString("name")
            ChooseCharacter(navController = navController, itemId = itemId, name = name)
        }
        composable(route = Screen.CompareCharacters.route) {
            val itemId1 = navController.currentBackStackEntry?.arguments?.getString("itemId1")
            val itemId2 = navController.currentBackStackEntry?.arguments?.getString("itemId2")
            CompareCharactersScreen(navController = navController, itemId1 = itemId1, itemId2 = itemId2)
        }
    }
}