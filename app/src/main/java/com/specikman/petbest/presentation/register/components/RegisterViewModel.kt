package com.specikman.petbest.presentation.register.components

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.use_case.login.LoginWithEmailUseCase
import com.specikman.petbest.domain.use_case.register.RegisterUseCase
import com.specikman.petbest.presentation.login.components.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(RegisterState())
    val state: State<RegisterState> = _state

    fun register(
        email: String,
        password: String,
        phone: String,
        name: String,
        context: Context,
        navController: NavController
    ) {
        registerUseCase(email = email, password = password, phone = phone, name = name, context = context, navController = navController).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = RegisterState(data = result.data ?: "")
                }
                is Resource.Error -> {
                    _state.value =
                        RegisterState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = RegisterState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}