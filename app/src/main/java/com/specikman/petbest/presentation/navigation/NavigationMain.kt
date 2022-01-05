package com.specikman.petbest.presentation.navigation

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.specikman.petbest.presentation.main_screen.components.ProductDetail
import com.specikman.petbest.presentation.main_screen.components.AllProducts
import com.specikman.petbest.presentation.main_screen.components.Home
import com.specikman.petbest.presentation.main_screen.view_models.HomeViewModel

@Composable
fun NavigationMain(navController: NavHostController) {
    val viewModel: HomeViewModel = hiltViewModel()
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            Home(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.AllProducts.route) {
            AllProducts(navController = navController, viewModel = viewModel)
        }
        composable(route = Screen.Settings.route) {
            Settings()
        }
        composable(route = Screen.ProductDetail.route){
            ProductDetail(navController = navController, viewModel = viewModel)
        }
    }
}

@Composable
fun Settings() {
    Text(text = "Settings")
}