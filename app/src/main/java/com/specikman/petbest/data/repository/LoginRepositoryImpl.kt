package com.specikman.petbest.data.repository
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.specikman.petbest.data.remote.firebase.FirebaseAPI
import com.specikman.petbest.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api : FirebaseAPI
) : LoginRepository{
    override suspend fun loginWithEmail(email: String, password: String) {
        api.loginWithEmail(email = email, password = password)
    }

    override suspend fun sendForgotPasswordEmail(email: String) {
        api.sendForgotPasswordEmail(email = email)
    }
}