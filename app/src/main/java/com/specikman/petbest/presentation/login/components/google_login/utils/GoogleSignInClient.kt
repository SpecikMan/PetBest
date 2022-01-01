package com.specikman.petbest.presentation.login.components.google_login.utils

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.specikman.petbest.R

fun getGoogleSignInClient(context: Context): GoogleSignInClient{
    val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(context.getString(R.string.client_id))
        .requestEmail()
        .requestProfile()
        .build()
    return GoogleSignIn.getClient(context, signInOptions)
}