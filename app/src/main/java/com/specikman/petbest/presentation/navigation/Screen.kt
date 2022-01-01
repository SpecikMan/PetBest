package com.specikman.petbest.presentation.navigation

sealed class Screen(val route : String) {
    object LoginScreen : Screen("login_page")
    object RegisterScreen : Screen("register_page")
    object ForgotPasswordScreen : Screen("forgot_password")
    object ExtraInfoForGoogle : Screen("extra_info_for_google")
}