package com.specikman.petbest.presentation.navigation

import android.content.Context
import android.widget.Toast
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.specikman.petbest.presentation.main_screen.components.ProductDetail
import com.specikman.petbest.presentation.main_screen.components.AllProducts
import com.specikman.petbest.presentation.main_screen.components.Cart
import com.specikman.petbest.presentation.main_screen.components.Home
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
        composable(route = Screen.Settings.route) {
            Settings()
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
    }
}

@Composable
fun Settings() {
    Text(text = "Settings")
}