package com.specikman.petbest.presentation.login.components

data class LoginState(
    val isLoading : Boolean = false,
    val data: String = "",
    val error: String = ""
)