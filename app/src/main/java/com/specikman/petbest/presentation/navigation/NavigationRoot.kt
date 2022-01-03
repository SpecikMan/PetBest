package com.specikman.petbest.presentation.navigation

import android.content.Context
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.specikman.petbest.presentation.forgot_password.components.ForgotPasswordPage
import com.specikman.petbest.presentation.login.components.LoginPage
import com.specikman.petbest.presentation.login.components.google_login.utils.ExtraInfoForGoogle
import com.specikman.petbest.presentation.main_screen.components.Home
import com.specikman.petbest.presentation.main_screen.components.MainScreen
import com.specikman.petbest.presentation.register.components.RegisterPage

@ExperimentalAnimationApi
@Composable
fun NavigationRoot(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.LoginScreen.route){
            LoginPage(navController = navController , context = context)
        }
        composable(route = Screen.RegisterScreen.route){
            RegisterPage(navController = navController, context = context)
        }
        composable(route = Screen.ForgotPasswordScreen.route){
            ForgotPasswordPage(navController = navController, context = context)
        }
        composable(route = Screen.ExtraInfoForGoogle.route){
            ExtraInfoForGoogle(navController = navController, context = context)
        }
        composable(route = Screen.MainScreen.route){
            MainScreen(context = context)
        }

    }
}
