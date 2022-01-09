package com.specikman.petbest.presentation.main_screen.state

import com.specikman.petbest.domain.model.User

data class UsersState(
    val isLoading: Boolean = false,
    val users: List<User> = emptyList(),
    val error: String = ""
)