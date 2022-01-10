package com.specikman.petbest.presentation.navigation

sealed class Screen(val route : String) {
    object LoginScreen : Screen("login_page")
    object RegisterScreen : Screen("register_page")
    object ForgotPasswordScreen : Screen("forgot_password")
    object ExtraInfoForGoogle : Screen("extra_info_for_google")
    object MainScreen : Screen("main_screen")
    object Home : Screen("home")
    object AllProducts : Screen("all_products")
    object ProductDetail: Screen("product_detail")
    object QRScanner: Screen("qr_scanner")
    object CartScreen: Screen("cart_screen")
    object OrderScreen: Screen("order_screen")
    object PetCareScreen: Screen("pet_care_screen")
    object OrderDetailScreen: Screen("pet_care_detail_screen")
    object SplashScreen: Screen("splash_screen")
    object FavoriteScreen: Screen("favorite_screen")
    object SettingScreen: Screen("setting")
    object AccountScreen: Screen("account_screen")
    object HistoryScreen: Screen("history_screen")
    object TransferMoney: Screen("transfer_money")
}