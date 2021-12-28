package com.specikman.petbest.navigation

sealed class Screen(val route : String) {
    object LoginScreen : Screen("login_page")
    object RegisterScreen : Screen("register_page")
}