package com.specikman.petbest.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.specikman.petbest.composables.loginComps.LoginPage
import com.specikman.petbest.composables.loginComps.RegisterPage

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route){
            LoginPage(navController = navController , context)
        }
        composable(route = Screen.RegisterScreen.route){
            RegisterPage(navController = navController, context)
        }
    }
}