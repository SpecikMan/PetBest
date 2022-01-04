package com.specikman.petbest.presentation.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.specikman.petbest.presentation.main_screen.components.Home

@Composable
fun NavigationMain(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            Home(navController = navController)
        }
        composable(route = Screen.Search.route){
            Search()
        }
        composable(route = Screen.Settings.route){
            Settings()
        }
    }
}


@Composable
fun Search() {
    Text(text = "Search")
}

@Composable
fun Settings() {
    Text(text = "Settings")
}