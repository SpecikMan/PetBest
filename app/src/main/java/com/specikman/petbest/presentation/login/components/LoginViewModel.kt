package com.specikman.petbest.presentation.login.components

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.use_case.login.LoginWithEmailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithEmailUseCase : LoginWithEmailUseCase,
) : ViewModel(){
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private fun loginWithEmail(
        email: String,
        password: String
    ){
        loginWithEmailUseCase(email = email, password = password).onEach { result ->
            when(result){
                is Resource.Success -> {
                    _state.value = LoginState(data = result.data ?: "")
                }
                is Resource.Error -> {
                    _state.value = LoginState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun login(email: String, password: String){
        loginWithEmail(email = email, password = password)
    }
}