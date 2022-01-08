package com.specikman.petbest.presentation.forgot_password.components

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.specikman.petbest.domain.use_case.login_use_cases.forgot_password.ForgotPasswordUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val forgotPasswordUseCase: ForgotPasswordUseCase
) : ViewModel(){
    fun sendForgotPasswordEmail(
        email: String,
        context: Context
    ){
        forgotPasswordUseCase(email = email, context = context).launchIn(viewModelScope)
    }
}