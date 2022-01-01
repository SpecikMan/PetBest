package com.specikman.petbest.data.remote.firebase


import android.content.res.Resources
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.api.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.google.firebase.ktx.options
import com.specikman.petbest.R
import com.specikman.petbest.domain.model.User
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.prefs.Preferences

class FirebaseAPI {
    //
    private val TAG = "LOGIN"
    private lateinit var auth: FirebaseAuth
    private val userCollectionRef = Firebase.firestore.collection("users")

    //
    suspend fun loginWithEmail(
        email: String,
        password: String,
    ) {
        auth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }
        }
    }

    suspend fun register(
        email: String,
        password: String,
        phone: String,
        name: String
    ) {
        auth = FirebaseAuth.getInstance()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()
                    Log.d(TAG, "Current UID: ${auth.currentUser?.uid}")
                    saveUser(
                        User(
                            email = email,
                            phone = phone,
                            name = name,
                            uid = auth.currentUser?.uid ?: "NaN"
                        )
                    )
                } catch (e: Exception) {
                    Log.e(TAG, e.toString())
                }
            }
        }
    }

    private suspend fun saveUser(user: User) {
        try {
            userCollectionRef.add(user).await()
            Log.d(TAG, "Current UID: ${auth.currentUser?.uid}. Save success")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun sendForgotPasswordEmail(
        email : String
    ){
        auth = FirebaseAuth.getInstance()
        if(email.isNotBlank()){
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.sendPasswordResetEmail(email)
                }catch(e: Exception){
                    e.printStackTrace()
                }
            }
        }

    }
}