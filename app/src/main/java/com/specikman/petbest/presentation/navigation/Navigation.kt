package com.specikman.petbest.presentation.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.specikman.petbest.presentation.login.components.LoginPage
import com.specikman.petbest.presentation.register.components.RegisterPage

@Composable
fun Navigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
        composable(route = Screen.LoginScreen.route){
            LoginPage(navController = navController , context = context)
        }
        composable(route = Screen.RegisterScreen.route){
            RegisterPage(navController = navController, context = context)
        }
    }
}