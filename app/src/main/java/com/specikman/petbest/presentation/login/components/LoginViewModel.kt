package com.specikman.petbest.presentation.login.components

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.specikman.petbest.common.Resource
import com.specikman.petbest.domain.model.User
import com.specikman.petbest.domain.use_case.login.LoginWithEmailUseCase
import com.specikman.petbest.presentation.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginWithEmailUseCase: LoginWithEmailUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(LoginState())
    val state: State<LoginState> = _state

    private fun loginWithEmail(
        email: String,
        password: String
    ) {
        loginWithEmailUseCase(email = email, password = password).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = LoginState(data = result.data ?: "")
                }
                is Resource.Error -> {
                    _state.value =
                        LoginState(error = result.message ?: "An unexpected error occurred")
                }
                is Resource.Loading -> {
                    _state.value = LoginState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun login(email: String, password: String) {
        loginWithEmail(email = email, password = password)
    }

    private val auth = FirebaseAuth.getInstance()
    fun googleAuthForFirebase(
        account: GoogleSignInAccount,
        context: Context,
        navController: NavController
    ) {
        val credentials = GoogleAuthProvider.getCredential(account.idToken, null)
        CoroutineScope(Dispatchers.IO).launch {
            try {
                auth.signInWithCredential(credentials).await()
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Login Success",
                        Toast.LENGTH_LONG
                    ).show()
                    val currentEmail = auth.currentUser?.email ?: ""
                    val querySnapshot =
                        userCollectionRef.whereEqualTo("email",currentEmail).get().await()
                    if (querySnapshot.isEmpty) {
                        navController.navigate(Screen.ExtraInfoForGoogle.route) {
                            launchSingleTop = true
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "To main screen", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private val userCollectionRef = Firebase.firestore.collection("users")
    fun saveExtraInfo(
        phone: String,
        name: String,
        context: Context
    ) = CoroutineScope(Dispatchers.IO).launch {
        auth.currentUser?.let {
            val currentEmail = it.email ?: ""
            val uid = it.uid
            val querySnapshot =
                userCollectionRef.whereEqualTo("email",currentEmail).get().await()
            if (querySnapshot.isEmpty) {
                try {
                    userCollectionRef.add(
                        User(
                            email = currentEmail,
                            name = name,
                            phone = phone,
                            uid = uid
                        )
                    ).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Lưu thành công", Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}