package com.specikman.petbest.presentation.register.components

data class RegisterState(
    val isLoading : Boolean = false,
    val data: String = "",
    val error: String = ""
) {
}