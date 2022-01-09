package com.specikman.petbest.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.specikman.petbest.presentation.main_screen.components.*
import com.specikman.petbest.presentation.main_screen.view_models.ImageViewModel
import com.specikman.petbest.presentation.qrscanner.components.QRScanner

@ExperimentalPermissionsApi
@Composable
fun NavigationMain(navController: NavHostController, context: Context, viewModel: ImageViewModel) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            Home(navController = navController, imageViewModel = viewModel)
        }
        composable(route = Screen.AllProducts.route) {
            AllProducts(navController = navController, imageViewModel = viewModel)
        }
        composable(route = Screen.ProductDetail.route) {
            ProductDetail(
                navController = navController,
                imageViewModel = viewModel,
                context = context
            )
        }
        composable(route = Screen.QRScanner.route) {
            QRScanner(context = context, navController = navController, imageViewModel = viewModel)
        }
        composable(route = Screen.CartScreen.route) {
            Cart(imageViewModel = viewModel)
        }
        composable(route = Screen.OrderScreen.route){
            OrderScreen(imageViewModel = viewModel, navController = navController)
        }
        composable(route = Screen.OrderDetailScreen.route){
            OrderDetail(imageViewModel = viewModel)
        }
        composable(route = Screen.PetCareScreen.route){
            PetCare(context = context, navController = navController)
        }
        composable(route = Screen.FavoriteScreen.route){
            FavoriteScreen(imageViewModel = viewModel, navController = navController)
        }
        composable(route = Screen.SettingScreen.route){
            SettingScreen(context = context, navController = navController)
        }
        composable(route = Screen.AccountScreen.route){
            AccountScreen(context = context)
        }
        composable(route = Screen.HistoryScreen.route){
            HistoryScreen(imageViewModel = viewModel , navController = navController)
        }
    }
}
